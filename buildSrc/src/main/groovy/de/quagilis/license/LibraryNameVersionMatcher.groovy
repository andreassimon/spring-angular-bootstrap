package de.quagilis.license

import groovy.transform.Canonical


@Canonical
class LibraryNameVersionMatcher implements Matcher {

    String libraryName
    String libraryVersion

    @Override
    boolean matches(LibraryDescription libraryDescription) {
        libraryName == libraryDescription.name &&
        libraryVersion == libraryDescription.version
    }

}
