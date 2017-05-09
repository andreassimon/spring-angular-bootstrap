package de.quagilis.license

import groovy.transform.Immutable


@Immutable
class QuestionableLicense extends License {

    String name
    String reference
    String licenseText

    @Override
    boolean isCompliant() {
        return false
    }

    @Override
    boolean isQuestionable() {
        return true
    }

}
