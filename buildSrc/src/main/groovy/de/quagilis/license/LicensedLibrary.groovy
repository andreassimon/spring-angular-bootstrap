package de.quagilis.license

import groovy.transform.Immutable


@Immutable
class LicensedLibrary implements Library {

    String name
    String version
    License license

    String getLicenseReference() {
        license?.reference
    }

    @Override
    boolean isCompliant() {
        return license.isCompliant()
    }

    @Override
    boolean isQuestionable() {
        return license.isQuestionable()
    }

    @Override
    boolean isIncompliant() {
        return false
    }

    @Override
    boolean hasLicenseText() {
        return license.hasText()
    }

    String getLicenseName() {
        return license.name
    }

}

