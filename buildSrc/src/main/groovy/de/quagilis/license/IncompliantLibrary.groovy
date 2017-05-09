package de.quagilis.license

import groovy.transform.Canonical


@Canonical
class IncompliantLibrary implements Library {

    String name
    String version
    String licenseString

    @Override
    boolean isCompliant() {
        return false
    }

}
