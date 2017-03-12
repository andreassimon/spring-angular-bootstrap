package de.quagilis.license


@groovy.util.logging.Log
class DependencyParser {

    static Optional<Library> fromNpmLine(String npmLine) {
        def matcher = (npmLine =~ /([^\t]*) \(([^\)]*)\)\t([^\t]*).*/)
        if (!matcher.matches()) {
            log.warn "Cannot match '$npmLine'"
            return Optional.empty()
        }
        return Optional.of(new de.quagilis.license.Library(
            name: matcher[0][1],
            version: matcher[0][2],
            licenses: parseNpmLicenses(matcher[0][3] as String)
        ))
    }

    static List<License> parseNpmLicenses(String licenses) {
        switch (licenses) {
            case 'Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html)': return [ License.Apache2() ]
            case 'bsd 2 clause or mit or apache 2.0': return [ License.BSD2(), License.MIT(), License.Apache2() ]
            case 'BSD 2-clause "Simplified" License (http://www.opensource.org/licenses/BSD-2-Clause)': return [ License.BSD2()]
            case 'bsd 3 clause and mit': return [ License.BSD3(), License.MIT() ]
            case 'BSD 3-clause "New" or "Revised" License (http://www.opensource.org/licenses/BSD-3-Clause)': return [ License.BSD3() ]
            case 'MIT (http  : //www.opensource.org/licenses/MIT)': return [ License.MIT() ]
        }
        def matcher = (licenses =~ /(, )?([\w\s\.\*]+) \(([^)]+)\)+.*/)
        if(!matcher.matches()) {
            throw new RuntimeException("Cannot match licenses '$licenses'")
        }
        log.info("=== ${matcher[0]}")
        return [new License(
            name: matcher[0][2],
            links: ((String) matcher[0][3]).split(', *').collect { new URL(it) }
        )]
    }

}
