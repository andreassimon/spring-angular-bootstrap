package de.quagilis.license

import groovy.transform.Immutable

import static java.util.Collections.emptyList

@Immutable(knownImmutables = ['link'])
class License {
    String name
    String reference
    Collection<URL> links

    static License Apache2() {
        return new License(name: 'Apache License 2.0', reference: '<<apache-2.0>>', links: [
            new URL('http://www.apache.org/licenses/LICENSE-2.0'),
            new URL('http://www.opensource.org/licenses/Apache-2.0')
        ])
    }

    static License BSD2() {
        return new License (name: '2-Clause BSD License', reference: '<<bsd-2-clause>>', links: [
            new URL('http://www.opensource.org/licenses/BSD-2-Clause')
        ])
    }

    static License BSD3() {
        return new License(name: '3-Clause BSD License', reference: '<<bsd-3-clause>>', links: [
            new URL('https://opensource.org/licenses/BSD-3-Clause')
        ])
    }

    static License doWhatTheFuckYouWantPublicLicense() {
        return new License(name: 'Do What The F*ck You Want To Public License', reference: '<<do-what-the-fuck-you-want-license>>', links: [
            new URL('http://sam.zoy.org/wtfpl/COPYING')
        ])
    }

    static def ISC() {
        return new License(name: 'ISC License', reference: '<<isc>>', links: [
            new URL('http://www.isc.org/software/license'),
            new URL('http://www.opensource.org/licenses/ISC')
        ])
    }

    static License MIT() {
        return new License (name: 'MIT License', reference: '<<mit>>', links: [
            new URL('http://www.opensource.org/licenses/MIT')
        ])
    }

    static def Zlib() {
        return new License (name: 'The zlib/libpng License', reference: '<<zlib>>', links: [
            new URL('http://www.zlib.net/zlib_license.html'),
            new URL('http://www.opensource.org/licenses/MIT')
        ])
    }
}
