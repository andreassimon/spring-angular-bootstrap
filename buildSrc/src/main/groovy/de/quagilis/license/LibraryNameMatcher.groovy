package de.quagilis.license

import groovy.transform.Canonical


@Canonical
class LibraryNameMatcher implements Matcher {

    String libraryName

    @Override
    boolean matches(LibraryDescription libraryDescription) {
        libraryName == libraryDescription.name
    }

}
