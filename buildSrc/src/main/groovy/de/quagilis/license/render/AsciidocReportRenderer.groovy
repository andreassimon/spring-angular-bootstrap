package de.quagilis.license.render

import com.github.jk1.license.ImportedModuleData
import com.github.jk1.license.LicenseReportPlugin.LicenseReportExtension
import com.github.jk1.license.ModuleData
import com.github.jk1.license.ProjectData
import org.gradle.api.Project


class AsciidocReportRenderer extends com.github.jk1.license.render.SingleInfoReportRenderer {

    private String fileName = 'third-party-libraries.adoc'
    private String chapterName = 'Third-party libraries'
    private Project project
    private LicenseReportExtension config
    private File output

    public AsciidocReportRenderer() {
    }

    AsciidocReportRenderer(String fileName, String chapterName) {
        this.fileName = fileName
        this.chapterName = chapterName
    }

    def void render(ProjectData data) {
        project = data.project
        config = project.licenseReport
        output = new File(fileName)

        output.text = "== $chapterName\n\n"

        output << tableHeader()
        data.allDependencies.sort().each {
            printDependency(it)
        }
        output << "|===\n"

        data.importedModules.each {
            output << "[${it.name.replaceAll(' ', '_')}]\n"
            output << "== $it.name\n"
            output << tableHeader()
            it.modules.each { printImportedModule(it) }
            output << "|===\n"
        }
    }

    public String tableHeader() {
        "[cols=\"5,2,4\",options=\"header\"]\n" +
        "|===\n" +
        "| Name | Version | Lizenz\n"
    }

    private def void printDependency(ModuleData data) {
        def moduleName = "${data.group}:${data.name}"
        def moduleVersion = data.version
        def (String moduleUrl, String moduleLicense, String moduleLicenseUrl) = moduleLicenseInfo(config, data)

        if (moduleUrl) {
            output << "| $moduleUrl[$moduleName]"
        } else {
            output << "| $moduleName"
        }
        output << " | $moduleVersion"
        if (moduleLicense) {
            if (moduleLicenseUrl) {
                output << " | $moduleLicenseUrl[$moduleLicense]\n"
            } else {
                output << " | $moduleLicense\n"
            }
        } else {
            output << " | *No license information found*\n"
        }
    }

    private def void printImportedModule(ImportedModuleData data) {
        if (data.projectUrl) {
            output << "| $data.projectUrl[$data.name]"
        } else {
            output << "| $data.name"
        }
        output << " | $data.version"
        if (data.license) {
            if (data.licenseUrl) {
                output << " | $data.licenseUrl[$data.license]\n"
            } else {
                output << " | $data.license\n"
            }
        } else {
            output << " | *No license information found*\n"
        }
    }

}


