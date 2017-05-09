package de.quagilis.license

import groovy.transform.Canonical


@Canonical
class LicenseStringMatcher implements Matcher {

    final String licenseString

    boolean matches(LibraryDescription libraryDescription) {
        licenseString == libraryDescription.license
    }

}
