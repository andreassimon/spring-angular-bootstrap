package de.quagilis.license


class LicensePluginExtension {

    boolean failOnForbiddenLicenses = false

    final List<License> compliantLicenses = []


    void license(Map<String, String> licenseAttributes, Closure matchers) {
        license(new License(
            name: licenseAttributes.name,
            reference: licenseAttributes.reference,
            licenseText: licenseAttributes.licenseFile.text
        ), matchers)
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
