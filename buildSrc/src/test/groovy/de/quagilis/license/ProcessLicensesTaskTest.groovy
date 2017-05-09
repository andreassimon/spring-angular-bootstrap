package de.quagilis.license

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder


class ProcessLicensesTaskTest extends GroovyTestCase {

    Project project

    @Override
    void setUp() {
        super.setUp()

        project = ProjectBuilder.builder().build()
        project.apply plugin: de.quagilis.license.LicensePlugin
    }

    void testParseLibraryLine() {
        assert ProcessLicensesTask.parseLibrary("Logback Classic Module (1.1.11)\thttp://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]\thttp://www.qos.ch") == new LibraryDescription(
            name: 'Logback Classic Module',
            version: '1.1.11',
            license: 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        )
    }

    // TODO Handle parsing lines not matching

    void testMatchCompliantLicense() {
        def expectedLibraryName = 'Logback Classic Module'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        def expectedLicense = LGPL2_1()

        project.licenses.license(expectedLicense) {
            matchLicense licenseString
        }

        assert project.frontendLicenses.checkForCompliance(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new CompliantLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            expectedLicense
        )

    }

    static License LGPL2_1() {
        return new License(name: 'GNU Lesser General Public License 2.1', reference: 'lgpl-2.1-license')
    }

    void testMatchIncompliantLicense() {
        def expectedLibraryName = 'Logback Classic Module'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'

        assert project.frontendLicenses.checkForCompliance(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new IncompliantLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            licenseString
        )

    }

    void testMatchLibrary() {
        def expectedLibraryName = 'Logback Classic Module'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        def expectedLicense = LGPL2_1()

        project.licenses.license(expectedLicense) {
            matchLibrary expectedLibraryName
        }

        assert project.frontendLicenses.checkForCompliance(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new CompliantLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            expectedLicense
        )
    }
}
