package de.quagilis.license

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


class ProcessLicensesTask extends DefaultTask {

    public static final String MATCH_LICENSE = 'matchLicense'

    @InputFile
    File input

    @OutputFile
    File output

    Map<Map<String, String>, License> allowedLicenses = [:]

    ProcessLicensesTask() {
        group = 'Documentation'
        description = 'Reads a tab-separated list of libraries, checks for compliance, and writes an Asciidoctor file'
    }

    @TaskAction
    def processLicenses() {
        List<Library> libraries = input.collect { line ->
            def matcher = (line =~ /([^\t]*) \(([^\)]*)\)\t([^\t]*).*/)
            if (!matcher.matches()) {
                throw new RuntimeException("Cannot match '$line'")
            }
            return new Library(
                name: matcher[0][1] as String,
                version: matcher[0][2] as String,
                licenseReference: normalizeLicenses(matcher[0][1] as String, matcher[0][3] as String, project.licenses.failOnForbiddenLicenses).reference
            )
        }.sort { Library a, Library b -> (a.name <=> b.name) }

        output.text = toAsciidoctor(libraries)
    }

    String toAsciidoctor(List<Library> libraries) {
        """\
[cols="5,2,6",options="header"]
|===
| Name | Version | Lizenz
${ libraries.collect { "| ${it.name} | ${it.version} | ${it.licenseReference}" }.join('\n') }
|===
"""
    }

    License normalizeLicenses(String library, String licenses, boolean failOnForbiddenLicense) {
        if(!findMapping(allowedLicenses, licenses, library)) {
            if(failOnForbiddenLicense) {
                throw new RuntimeException("'$library' uses forbidden license '${licenses}'")
            } else {
                return License.forbidden(licenses)
            }
        }
        return findMapping(allowedLicenses, licenses, library).value
    }

    Map.Entry<Map<String, String>, License> findMapping(Map<Map<String, String>, License> allowedLicenses, String licenses, String library) {
        allowedLicenses.find { key, value -> matchesLicense(key, licenses, library) }
    }

    boolean matchesLicense(Map<String, String> matcher, String licenses, String library) {
        if (matcher.containsKey(MATCH_LICENSE)) {
            return matcher[MATCH_LICENSE] == licenses
        }
        if (matcher.containsKey('matchLibrary')) {
            return matcher['matchLibrary'] == library
        }
    }

}
