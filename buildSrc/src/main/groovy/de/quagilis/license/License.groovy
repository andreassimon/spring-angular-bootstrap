package de.quagilis.license

import groovy.transform.Immutable

import static java.util.Collections.emptyList

@Immutable(knownImmutables = ['link'])
class License {
    String name
    Collection<URL> links

    static License BSD2() {
        return new License (name: '2-Clause BSD License', links: [new URL('http://www.opensource.org/licenses/BSD-2-Clause')])
    }

    static License BSD3() {
        return new License(name: '3-Clause BSD License', links: [new URL('https://opensource.org/licenses/BSD-3-Clause')])
    }

    static License MIT() {
        return new License (name: 'MIT License', links: [new URL('http://www.opensource.org/licenses/MIT')])
    }

    static License Apache2() {
        return new License(name: 'Apache License 2.0', links: [
            new URL('http://www.apache.org/licenses/LICENSE-2.0'),
            new URL('http://www.opensource.org/licenses/Apache-2.0')
        ])
    }

}
