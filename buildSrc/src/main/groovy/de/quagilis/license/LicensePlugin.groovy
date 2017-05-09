package de.quagilis.license

import org.gradle.api.Plugin
import org.gradle.api.Project


class LicensePlugin implements Plugin<Project> {


    public static final String DOCUMENTATION = 'Documentation'

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

        project.tasks.create('compliantLicensesAsciidoc', { task ->
            task.group = DOCUMENTATION
            def taskOutput = project.file("src/docs/asciidoc/compliant-licenses.adoc")
            task.doLast {
               taskOutput.text =
                   project.licenses.compliantLicenses \
                   .collect { " * <<$it.reference>>\n" } \
                   .join()
            }
        })

        project.tasks.create('questionableLibrariesAsciidoc', { task ->
            task.group = DOCUMENTATION

            def taskOutput = project.file("src/docs/asciidoc/questionable-libraries.adoc")
            task.doLast {
                taskOutput.text = Asciidoctor.toAsciidoctor(project.licenses.questionableLibraries)
            }
        })

        project.tasks.create('compileLicenseTexts', { task ->
            task.doLast {
                project.file('src/docs/asciidoc/license-texts.adoc').text = """\
:leveloffset: +2
${ project.licenses.licenses.collect { license -> toAsciidoc(license) }.findAll().join('\n') }
:leveloffset: -2
"""
            }
        })

    }

    String toAsciidoc(license) {
        if(license.hasText()) {
            return """[[${license.reference}]]
${license.licenseText}"""
        } else {
            return null
        }
    }

}
