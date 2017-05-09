package de.quagilis.license

import groovy.transform.Immutable


@Immutable
class CompliantLicense extends License {

    String name
    String reference
    String licenseText

    @Override
    boolean isCompliant() {
        return true
    }

    @Override
    boolean isQuestionable() {
        return false
    }

}
