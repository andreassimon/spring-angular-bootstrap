package de.quagilis.license

class DependencyParserTest extends GroovyTestCase {

    void testParsesSimpleNpmDependencies() {
        def reflectLibrary = DependencyParser.fromNpmLine("reflect-metadata (0.1.9)	unknown (http://github.com/rbuckton/ReflectDecorators/raw/master/LICENSE)").get()

        assert reflectLibrary.name == 'reflect-metadata'
        assert reflectLibrary.version == '0.1.9'
        assert reflectLibrary.licenses[0].name == 'unknown'
        assert reflectLibrary.licenses[0].links == [new URL('http://github.com/rbuckton/ReflectDecorators/raw/master/LICENSE')]
    }

    void testParsesLicenseWithMultipleLinks() {
        def reflectLibrary = DependencyParser.fromNpmLine("reflect-metadata (0.1.9)	Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0, http://www.opensource.org/licenses/Apache-2.0)").get()

        assert reflectLibrary.name == 'reflect-metadata'
        assert reflectLibrary.version == '0.1.9'
        assert reflectLibrary.licenses[0] == License.Apache2()
    }

    void testSpecial() {
        def amdefineLibrary = DependencyParser.fromNpmLine("amdefine (1.0.0)	bsd 3 clause and mit	node_modules/remap-istanbul/node_modules/amdefine/package.json").get()

        assert amdefineLibrary.name == 'amdefine'
        assert amdefineLibrary.version == '1.0.0'
        assert amdefineLibrary.licenses[0] == License.BSD3()
        assert amdefineLibrary.licenses[1] == License.MIT()


        def charencLibrary = DependencyParser.fromNpmLine('charenc (0.0.2)	BSD 3-clause "New" or "Revised" License (http://www.opensource.org/licenses/BSD-3-Clause)	node_modules/charenc/package.json').get()

        assert charencLibrary.name == 'charenc'
        assert charencLibrary.version == '0.0.2'
        assert charencLibrary.licenses[0] == License.BSD3()


        def findupLibrary = DependencyParser.fromNpmLine('findup (0.1.5)	MIT (http  : //www.opensource.org/licenses/MIT)	node_modules/findup/package.json').get()

        assert findupLibrary.name == 'findup'
        assert findupLibrary.version == '0.1.5'
        assert findupLibrary.licenses[0] == License.MIT()
    }

    void x_testParsesLibraryWithMultipleLicenses() {
        def reflectLibrary = DependencyParser.fromNpmLine("reflect-metadata (0.1.9)	unknown (http://github.com/rbuckton/ReflectDecorators/raw/master/LICENSE), Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0, http://www.opensource.org/licenses/Apache-2.0)").get()

        assert reflectLibrary.name == 'reflect-metadata'
        assert reflectLibrary.version == '0.1.9'

        assert reflectLibrary.licenses.size() == 2
        assert reflectLibrary.licenses[0].name == 'unknown'
        assert reflectLibrary.licenses[0].links == [new URL('http://github.com/rbuckton/ReflectDecorators/raw/master/LICENSE')]

        assert reflectLibrary.licenses[1].name == 'Apache License 2.0'
        assert reflectLibrary.licenses[1].links == [
            new URL('http://www.apache.org/licenses/LICENSE-2.0'),
            new URL('http://www.opensource.org/licenses/Apache-2.0')
        ]
    }


    void testAngularLine() {
        def angularLibrary = DependencyParser.fromNpmLine("@angular/common (2.2.1)	MIT License (http://www.opensource.org/licenses/MIT)	node_modules/@angular/common/package.json").get()

        assert angularLibrary.name == '@angular/common'
        assert angularLibrary.version == '2.2.1'
        assert angularLibrary.licenses[0] == License.MIT()
    }

}
