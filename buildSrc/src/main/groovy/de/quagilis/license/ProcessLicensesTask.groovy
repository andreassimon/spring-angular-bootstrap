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

    Map<String, License> allowedLicenses = [:]

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
                licenseReference: normalizeLicenses(matcher[0][1] as String, matcher[0][3] as String).reference
            )
        }.sort { Library a, Library b -> a.name.compareTo(b.name) }

        output.text = """\
[cols="5,2,6",options="header"]
|===
| Name | Version | Lizenz
${ ((List<License>) libraries).collect { "| ${it.name} | ${it.version} | ${it.licenseReference}" }.join('\n') }
|===
"""
    }

    License normalizeLicenses(String library, String licenses) {
        if(!((Map<String, License>) allowedLicenses).containsKey(licenses)) {
            throw new RuntimeException("'$library' uses forbidden license '${licenses}'")
        }
        return allowedLicenses[licenses]
    }

}
