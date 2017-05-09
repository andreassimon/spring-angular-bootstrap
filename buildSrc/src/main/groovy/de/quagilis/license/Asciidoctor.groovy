package de.quagilis.license


class Asciidoctor {
    static String toAsciidoctor(List<Library> libraries) {
        """\
[cols="5,2,6",options="header"]
|===
| Name | Version | Lizenz
${ libraries.sort { Library a, Library b -> (a.name <=> b.name) }.collect { toAsciidoctor(it) }.join('\n') }
|===
"""
    }

    static String toAsciidoctor(LicensedLibrary library) {
        if (library.hasLicenseText()) {
            return "| ${library.name} | ${library.version} | <<${library.licenseReference}>>"
        } else {
            return "| ${library.name} | ${library.version} | ${library.licenseName}"
        }
    }

    static String toAsciidoctor(UnlicensedLibrary library) {
        "| ${library.name} | ${library.version} | Incompliant: ${library.licenseString}"
    }
}
