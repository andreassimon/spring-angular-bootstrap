package de.quagilis.license

import groovy.transform.Immutable
import groovy.transform.Memoized


@Immutable(knownImmutables = ['link'])
class License {
    String name
    String reference
    Collection<URL> links

    private Collection<?> matchers = []

    def matchLicense(String licenseString) {
        matchers << [matchLicense: licenseString]
    }




    @Memoized
    static License Apache2() {
        return new License(name: 'Apache License 2.0', reference: '<<apache-2.0>>', links: [
            new URL('http://www.apache.org/licenses/LICENSE-2.0'),
            new URL('http://www.opensource.org/licenses/Apache-2.0')
        ])
    }

    @Memoized
    static License BSD2() {
        return new License (name: '2-Clause BSD License', reference: '<<bsd-2-clause>>', links: [
            new URL('http://www.opensource.org/licenses/BSD-2-Clause')
        ])
    }

    @Memoized
    static License BSD3() {
        return new License(name: '3-Clause BSD License', reference: '<<bsd-3-clause>>', links: [
            new URL('https://opensource.org/licenses/BSD-3-Clause')
        ])
    }

    @Memoized
    static License CC0() {
        return new License(name: 'CC0 1.0 Universal', reference: '<<CC0-1.0-license>>', links: [
            new URL('http://repository.jboss.org/licenses/cc0-1.0.txt')
        ])
    }


    @Memoized
    static License CC_4_0() {
        return new License(name: 'Creative Commons Attribution 4.0', reference: '<<CC-Attribution-4.0-license>>', links: [
            new URL('https://creativecommons.org/licenses/by/4.0/legalcode')
        ])
    }


    @Memoized
    static License doWhatTheFuckYouWantPublicLicense() {
        return new License(name: 'Do What The F*ck You Want To Public License', reference: '<<do-what-the-fuck-you-want-license>>', links: [
            new URL('http://sam.zoy.org/wtfpl/COPYING')
        ])
    }

    @Memoized
    static def ISC() {
        return new License(name: 'ISC License', reference: '<<isc>>', links: [
            new URL('http://www.isc.org/software/license'),
            new URL('http://www.opensource.org/licenses/ISC')
        ])
    }

    @Memoized
    static def LGPL2_1() {
        return new License(name: 'GNU Lesser General Public License 2.1', reference: '<<lgpl-2.1-license>>', links: [
            new URL('https://opensource.org/licenses/LGPL-2.1'),
            new URL('http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html')
        ])
    }

    @Memoized
    static def LGPL3_0() {
        return new License(name: 'GNU Lesser General Public License 3.0', reference: '<<lgpl-3.0-license>>', links: [
            new URL('http://www.gnu.org/licenses/lgpl.html')
        ])
    }

    @Memoized
    static License MIT() {
        return new License (name: 'MIT License', reference: '<<mit>>', links: [
            new URL('http://www.opensource.org/licenses/MIT')
        ])
    }

    @Memoized
    static def Zlib() {
        return new License (name: 'The zlib/libpng License', reference: '<<zlib>>', links: [
            new URL('http://www.zlib.net/zlib_license.html'),
            new URL('http://www.opensource.org/licenses/MIT')
        ])
    }

    @Memoized
    static def MISSING() {
        return new License(name: 'MISSING', reference: "*_FORBIDDEN:_* MISSING")
    }

    static License forbidden(String forbiddenLicense) {
        return new License(reference: "*_FORBIDDEN:_* $forbiddenLicense")
    }

}
