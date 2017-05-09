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
            project.licenses.assignLicense(parseLibrary(line))
        }

        List<Library> incompliantLibraries = libraries.findAll { Library it -> it.isIncompliant()}

        if (!incompliantLibraries.empty) {
            if(project.licenses.failOnForbiddenLicenses) {
                logger.error listIncompliantLibraries(incompliantLibraries)
                throw new RuntimeException('Found incompliant libraries')
            } else {
                println listIncompliantLibraries(incompliantLibraries)
            }
        }

        output.text = Asciidoctor.toAsciidoctor(libraries)
    }

    String listIncompliantLibraries(List<UnlicensedLibrary> incompliantLibraries) {
        """Found incompliant libraries:
${ incompliantLibraries.collect { UnlicensedLibrary it -> " - '$it.name' $it.version uses incompliant license '$it.licenseString'" }.join("\n") }
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

}
