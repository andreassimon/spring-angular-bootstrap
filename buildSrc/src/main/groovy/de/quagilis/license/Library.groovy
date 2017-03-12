package de.quagilis.license

import groovy.transform.Immutable

@Immutable
class Library {
    String name
    String version
    Collection<License> licenses
}

