package de.quagilis.license


class LicensePluginExtension {

    public static final String ASCIIDOC_TEXT = 'asciidocText'
    public static final String PLAIN_TEXT = 'plainText'

    boolean failOnForbiddenLicenses = false

    final List<License> compliantLicenses = []


    void license(Map<String, ?> licenseAttributes, Closure matchers) {
        license(new License(
            name: licenseAttributes.name,
            reference: licenseAttributes.reference,
            licenseText: licenseText(licenseAttributes)
        ), matchers)
    }

    String licenseText(Map<String, ?> licenseAttributes) {
        if(licenseAttributes.containsKey(ASCIIDOC_TEXT)) {
            return ((File) licenseAttributes[ASCIIDOC_TEXT]).text
        }
        if(licenseAttributes.containsKey(PLAIN_TEXT)) {
            return "= ${licenseAttributes.name}\n" + ((File) licenseAttributes[PLAIN_TEXT]).text
        }
        return ''
    }

    void license(License license, Closure matchers) {
        compliantLicenses << license
        matchers.delegate = license
        matchers()
    }

    License findLicense(LibraryDescription libraryDescription) {
        compliantLicenses.find { License license ->
            license.appliesTo(libraryDescription)
        }
    }

}
