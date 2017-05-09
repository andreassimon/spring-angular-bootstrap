package de.quagilis.license

interface Library {

    String getName()

    boolean isCompliant()

    boolean isQuestionable()

    boolean isIncompliant()

    boolean hasLicenseText()

}
