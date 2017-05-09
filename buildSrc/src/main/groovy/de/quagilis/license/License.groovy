package de.quagilis.license


abstract class License {

    private Collection<Matcher> matchers = []

    def matchLicense(String licenseString) {
        matchers << new LicenseStringMatcher(licenseString)
    }

    def matchLibrary(String libraryName) {
        matchers << new LibraryNameMatcher(libraryName)
    }

    def matchLibrary(String libraryName, String libraryVersion) {
        matchers << new LibraryNameVersionMatcher(libraryName, libraryVersion)
    }

    boolean appliesTo(LibraryDescription libraryDescription) {
        matchers.find { it.matches libraryDescription }
    }

    boolean hasText() {
        null != getLicenseText() && !getLicenseText().empty
    }

    abstract String getName()

    abstract String getLicenseText()

    abstract boolean isCompliant()

    abstract boolean isQuestionable()

}
