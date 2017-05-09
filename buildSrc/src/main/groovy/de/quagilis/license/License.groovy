package de.quagilis.license

import groovy.transform.Immutable


@Immutable
class License {
    String name
    String reference
    String licenseText

    private Collection<Matcher> matchers = []

    def matchLicense(String licenseString) {
        matchers << new LicenseStringMatcher(licenseString)
    }

    def matchLibrary(String libraryName) {
        matchers << new LibraryNameMatcher(libraryName)
    }

    boolean appliesTo(LibraryDescription libraryDescription) {
        matchers.find { it.matches libraryDescription }
    }

}
