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
            task.allowedLicenses = project.allowedLicenses
        })

        project.tasks.create('frontendLicenses', ProcessLicensesTask, { task ->
            task.input = project.rootProject.file("frontend/licenses.tsv")
            task.output = project.file("src/docs/asciidoc/frontend-licenses.adoc")
            task.allowedLicenses = project.allowedLicenses
        })

    }
}

class LicensePluginExtension {

    boolean failOnForbiddenLicenses = false

    def apache2(Closure matchers) {
        def apache2 = new License(name: 'Apache License 2.0', reference: '<<apache-2.0>>', links: [
            new URL('http://www.apache.org/licenses/LICENSE-2.0'),
            new URL('http://www.opensource.org/licenses/Apache-2.0')
        ])
        matchers.delegate = apache2
        matchers()
    }

}
