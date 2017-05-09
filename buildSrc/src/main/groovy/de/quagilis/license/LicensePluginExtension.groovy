package de.quagilis.license


class LicensePluginExtension {

    public static final String ASCIIDOC_TEXT = 'asciidocText'
    public static final String PLAIN_TEXT = 'plainText'

    boolean failOnForbiddenLicenses = false

    final List<License> licenses = []
    final List<LicensedLibrary> licensedLibraries = []


    void license(Map<String, ?> licenseAttributes, Closure matchers) {
        license(new CompliantLicense(
            name: licenseAttributes.name,
            reference: licenseAttributes.reference,
            licenseText: licenseText(licenseAttributes)
        ), matchers)
    }

    void questionableLicense(String licenseName, Closure matchers) {
        license(new QuestionableLicense( name: licenseName ), matchers)
    }

    void questionableLicense(Map<String, ?> licenseAttributes, Closure matchers) {
        license(new QuestionableLicense(
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
        licenses << license
        matchers.delegate = license
        matchers()
    }

    Library assignLicense(LibraryDescription libraryDescription) {
        License license = findMatchingLicense(libraryDescription)
        if (license) {
            LicensedLibrary library = new LicensedLibrary(
                libraryDescription.name,
                libraryDescription.version,
                license
            )
            licensedLibraries << library
            return library
        } else {
            return new UnlicensedLibrary(
                libraryDescription.name,
                libraryDescription.version,
                libraryDescription.license
            )
        }
    }

    License findMatchingLicense(LibraryDescription libraryDescription) {
        licenses.find { License license ->
            license.appliesTo(libraryDescription)
        }
    }

    List<CompliantLicense> getCompliantLicenses() {
        licenses.findAll { License it -> it.isCompliant() } as List<CompliantLicense>
    }

    List<LicensedLibrary> getQuestionableLibraries() {
        licensedLibraries.findAll { it.isQuestionable() }
    }

}
