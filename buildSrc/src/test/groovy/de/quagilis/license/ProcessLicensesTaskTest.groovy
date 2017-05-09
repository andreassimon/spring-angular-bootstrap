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
        def expectedLibraryName = 'Library Name'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        def expectedLicense = LGPL2_1()

        project.licenses.license(expectedLicense) {
            matchLicense licenseString
        }

        assert project.licenses.assignLicense(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new LicensedLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            expectedLicense
        )

    }

    static CompliantLicense LGPL2_1() {
        return new CompliantLicense(name: 'GNU Lesser General Public License 2.1', reference: 'lgpl-2.1-license')
    }

    void testMatchIncompliantLicense() {
        def expectedLibraryName = 'Library Name'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'

        assert project.licenses.assignLicense(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new UnlicensedLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            licenseString
        )

    }

    void testMatchLibrary() {
        def expectedLibraryName = 'Library Name'
        def expectedLibraryVersion = '1.1.11'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        def expectedLicense = LGPL2_1()

        project.licenses.license(expectedLicense) {
            matchLibrary expectedLibraryName
        }

        assert project.licenses.assignLicense(new LibraryDescription(
            name: expectedLibraryName,
            version: expectedLibraryVersion,
            license: licenseString
        )) == new LicensedLibrary(
            expectedLibraryName,
            expectedLibraryVersion,
            expectedLicense
        )
    }

    void testMatchIncompliantLibraryVersion() {
        def expectedLibraryName = 'Library Name'
        def compliantLibraryVersion = '1.1.11'
        def incompliantLibraryVersion = '1.2.0'
        def licenseString = 'http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html[GNU Lesser General Public License]'
        def expectedLicense = LGPL2_1()

        project.licenses.license(expectedLicense) {
            matchLibrary expectedLibraryName, compliantLibraryVersion
        }

        assert project.licenses.assignLicense(new LibraryDescription(
            name: expectedLibraryName,
            version: incompliantLibraryVersion,
            license: licenseString
        )) == new UnlicensedLibrary(
            expectedLibraryName,
            incompliantLibraryVersion,
            licenseString
        )
    }

}
