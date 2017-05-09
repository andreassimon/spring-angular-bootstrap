package de.quagilis.license

import groovy.transform.Canonical


@Canonical
class UnlicensedLibrary implements Library {

    String name
    String version
    String licenseString

    @Override
    boolean isCompliant() {
        return false
    }

    @Override
    boolean isQuestionable() {
        return false
    }

    @Override
    boolean isIncompliant() {
        return true
    }

    @Override
    boolean hasLicenseText() {
        return false
    }

}
