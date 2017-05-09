package de.quagilis.license

import groovy.transform.Immutable


@Immutable
class CompliantLibrary implements Library {

    String name
    String version
    License license

    String getLicenseReference() {
        license?.reference
    }

    @Override
    boolean isCompliant() {
        return true
    }

}

