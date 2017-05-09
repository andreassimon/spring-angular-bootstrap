package de.quagilis.license

import org.gradle.api.Plugin
import org.gradle.api.Project


class LicensePlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("licenses", LicensePluginExtension)

        project.tasks.create('backendLicenses', ProcessLicensesTask, { task ->
            task.dependsOn ':backend:generateLicenseReport'

            task.input = project.rootProject.file("backend/build/licenses.tsv")
            task.output = project.file("src/docs/asciidoc/backend-licenses.adoc")
        })

        project.tasks.create('frontendLicenses', ProcessLicensesTask, { task ->
            task.input = project.rootProject.file("frontend/licenses.tsv")
            task.output = project.file("src/docs/asciidoc/frontend-licenses.adoc")
        })

        project.tasks.create('allowedLicensesAsciidoc', { task ->
            task.group = 'Documentation'
            def taskOutput = project.file("src/docs/asciidoc/allowed-licenses.adoc")
            task.doLast {
               taskOutput.text =
                   project.licenses.compliantLicenses \
                   .collect { " * <<$it.reference>>\n" } \
                   .join()
            }
        })

        project.tasks.create('compileLicenseTexts', { task ->
            task.doLast {
                project.file('src/docs/asciidoc/license-texts.adoc').text = """\
:leveloffset: +2
${ project.licenses.compliantLicenses.collect { license ->
                    """[[${license.reference}]]
${ license.licenseText }"""
                }.join('\n') }
:leveloffset: -2
"""
            }
        })

    }

}
