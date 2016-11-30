package de.quagilis.license.render

import com.github.jk1.license.ImportedModuleData
import com.github.jk1.license.LicenseReportPlugin.LicenseReportExtension
import com.github.jk1.license.ModuleData
import com.github.jk1.license.ProjectData
import com.github.jk1.license.render.ReportRenderer
import org.gradle.api.Project


class AsciidocReportRenderer implements ReportRenderer {

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

    void render(ProjectData data) {
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

    String tableHeader() {
        "[cols=\"5,2,4\",options=\"header\"]\n" +
        "|===\n" +
        "| Name | Version | Lizenz\n"
    }

    private void printDependency(ModuleData data) {
        def moduleVersion = data.version
        def (String moduleUrl, String moduleLicense, String moduleLicenseUrl) = moduleLicenseInfo(config, data)

        output << "| "

        if (moduleUrl) {
            output << "$moduleUrl[${moduleName(data)}]"
        } else {
            output << moduleName(data)
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

    String moduleName(ModuleData data) {
        def moduleName = data.poms.collect { it.name }.join(', ')
        if(moduleName.empty) return "${data.group}:${data.name}"
        return moduleName
    }

    private void printImportedModule(ImportedModuleData data) {
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

    protected List<String> moduleLicenseInfo(LicenseReportExtension config, ModuleData data) {
        String moduleUrl = null
        String moduleLicense = null
        String moduleLicenseUrl = null

        data.manifests.each {
            if (it.url) {
                moduleUrl = it.url
            }
            if (it.license) {
                if (it.license.startsWith('http')) {
                    moduleLicenseUrl = it.license
                }
                moduleLicense = it.license

            }
        }
        data.poms.each {
            if (it.projectUrl) {
                moduleUrl = it.projectUrl
            }
            if (it.licenses) {
                it.licenses.each {
                    if (it.name) {
                        moduleLicense = it.name
                    }
                    if (it.url && it.url.startsWith('http')) {
                        moduleLicenseUrl = it.url
                    }
                }
            }
        }
        if (!moduleLicenseUrl) {
            data.licenseFiles.each {
                it.files.each {
                    def text = new File(config.outputDir, it).text
                    if (text.contains('Apache License, Version 2.0')) {
                        moduleLicense = 'Apache License, Version 2.0'
                        moduleLicenseUrl = 'http://www.apache.org/licenses/LICENSE-2.0'
                    }
                    if (text.contains('Apache Software License, Version 1.1')) {
                        moduleLicense = 'Apache Software License, Version 1.1'
                        moduleLicenseUrl = 'http://www.apache.org/licenses/LICENSE-1.1'
                    }
                    if (text.contains('CDDL')) {
                        moduleLicense = 'COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0'
                        moduleLicenseUrl = 'http://opensource.org/licenses/CDDL-1.0'
                    }
                }
            }
        }
        [moduleUrl, moduleLicense, moduleLicenseUrl]
    }

}


