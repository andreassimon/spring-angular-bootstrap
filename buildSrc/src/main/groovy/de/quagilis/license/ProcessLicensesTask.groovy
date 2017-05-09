package de.quagilis.license

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


class ProcessLicensesTask extends DefaultTask {

    @InputFile
    File input

    @OutputFile
    File output

    ProcessLicensesTask() {
        group = 'Documentation'
        description = 'Reads a tab-separated list of libraries, checks for compliance, and writes an Asciidoctor file'
    }

    @TaskAction
    def processLicenses() {
        List<Library> libraries = input.collect { line ->
            checkForCompliance(parseLibrary(line))
        }

        List<IncompliantLibrary> incompliantLibraries = libraries.findAll { Library it -> !it.isCompliant()}

        if (!incompliantLibraries.empty) {
            if(project.licenses.failOnForbiddenLicenses) {
                logger.error listIncompliantLibraries(incompliantLibraries)
                throw new RuntimeException('Found incompliant libraries')
            } else {
                println listIncompliantLibraries(incompliantLibraries)
            }
        }

        output.text = toAsciidoctor(libraries)
    }

    String listIncompliantLibraries(List<IncompliantLibrary> incompliantLibraries) {
        """Found incompliant libraries:
${ incompliantLibraries.collect { IncompliantLibrary it -> " - '$it.name' $it.version uses incompliant license '$it.licenseString'" }.join("\n") }
"""
    }

    static LibraryDescription parseLibrary(String library) {
        def matcher = (library =~ /([^\t]*) \(([^\)]*)\)\t([^\t]*).*/)
        new LibraryDescription(
            name: matcher[0][1] as String,
            version: matcher[0][2] as String,
            license: matcher[0][3] as String
        )
    }

    Library checkForCompliance(LibraryDescription libraryDescription) {
        License license = project.licenses.findLicense(libraryDescription)
        if (license) {
            return new CompliantLibrary(
                libraryDescription.name,
                libraryDescription.version,
                license
            )
        } else {
            return new IncompliantLibrary(
                libraryDescription.name,
                libraryDescription.version,
                libraryDescription.license
            )
        }
    }

    static String toAsciidoctor(List<Library> libraries) {
        """\
[cols="5,2,6",options="header"]
|===
| Name | Version | Lizenz
${ libraries.sort { Library a, Library b -> (a.name <=> b.name) }.collect { toAsciidoctor(it) }.join('\n') }
|===
"""
    }

    static String toAsciidoctor(CompliantLibrary library) {
        "| ${library.name} | ${library.version} | <<${library.licenseReference}>>"
    }

    static String toAsciidoctor(IncompliantLibrary library) {
        "| ${library.name} | ${library.version} | Incompliant: ${library.licenseString}"
    }

}
