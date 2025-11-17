package com.googlecode.cppcheclipse.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.cppcheclipse.core.command.Version.VersionType;

public class TestVersion {

	// compare with issue 47
	@Test
	public void testDevelopmentVersion() {
		String versionString = "cppcheck 1.55 dev";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(55, version.getMinorVersion());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidVersion() {
		new Version("1.2.3");
	}

	@Test
	public void testReleaseVersion() {
		String versionString = "cppcheck 1.55";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(55, version.getMinorVersion());
		assertEquals(0, version.getRevision());
		assertEquals(0, version.getPatch());
		assertEquals(VersionType.OPEN_SOURCE, version.getType());
	}

	@Test
	public void testReleaseVersionWithRevision() {
		String versionString = "cppcheck 1.2.3";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(2, version.getMinorVersion());
		assertEquals(3, version.getRevision());
		assertEquals(0, version.getPatch());
		assertEquals(VersionType.OPEN_SOURCE, version.getType());
	}

	@Test
	public void testReleaseVersionWithPatch() {
		String versionString = "cppcheck 1.2.3.4";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(2, version.getMinorVersion());
		assertEquals(3, version.getRevision());
		assertEquals(4, version.getPatch());
		assertEquals(VersionType.OPEN_SOURCE, version.getType());
	}

	@Test
	public void testReleaseVersionPremium() {
		String versionString = "cppcheck premium 1.2.3.4";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(2, version.getMinorVersion());
		assertEquals(3, version.getRevision());
		assertEquals(4, version.getPatch());
		assertEquals(VersionType.PREMIUM, version.getType());
	}

	@Test
	public void testReleaseVersionPremiumSafetyCertified() {
		String versionString = "cppcheck premium 1.2.3.4s";
		Version version = new Version(versionString);
		assertEquals(1, version.getMajorVersion());
		assertEquals(2, version.getMinorVersion());
		assertEquals(3, version.getRevision());
		assertEquals(4, version.getPatch());
		assertEquals(VersionType.PREMIUM_SAFETY_CERTIFIED, version.getType());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsGreaterThanCompareInvalidVersion() {
		Version version1 = new Version("cppcheck 1.3");
		Version version2 = new Version("cppcheck premium 2.3");
		version2.isGreaterThan(version1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsGreaterThanCompareInvalidVersion2() {
		Version version1 = new Version("cppcheck 1.3");
		Version version2 = new Version("cppcheck premium 2.3s");
		version2.isGreaterThan(version1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsGreaterThanCompareInvalidVersion3() {
		Version version1 = new Version("cppcheck premium 1.3");
		Version version2 = new Version("cppcheck premium 2.3s");
		version2.isGreaterThan(version1);
	}

	@Test
	public void testIsGreaterThanWithDifferentMajorVersions() {
		Version version1 = new Version("cppcheck 1.3");
		Version version2 = new Version("cppcheck 2.3");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumWithDifferentMajorVersions() {
		Version version1 = new Version("cppcheck premium 1.3");
		Version version2 = new Version("cppcheck premium 2.3");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCertifiedWithDifferentMajorVersions() {
		Version version1 = new Version("cppcheck premium 1.3s");
		Version version2 = new Version("cppcheck premium 2.3s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithSameMajorAndMinorVersions() {
		Version version1 = new Version("cppcheck 1.2");
		Version version2 = new Version("cppcheck 1.2");
		assertFalse(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumWithSameMajorAndMinorVersions() {
		Version version1 = new Version("cppcheck premium 1.2");
		Version version2 = new Version("cppcheck premium 1.2");
		assertFalse(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCerifiedWithSameMajorAndMinorVersions() {
		Version version1 = new Version("cppcheck premium 1.2s");
		Version version2 = new Version("cppcheck premium 1.2s");
		assertFalse(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanDifferentMinorVersions() {
		Version version1 = new Version("cppcheck 1.2");
		Version version2 = new Version("cppcheck 1.3");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumDifferentMinorVersions() {
		Version version1 = new Version("cppcheck premium 1.2");
		Version version2 = new Version("cppcheck premium 1.3");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCertifiedDifferentMinorVersions() {
		Version version1 = new Version("cppcheck premium 1.2s");
		Version version2 = new Version("cppcheck premium 1.3s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithDifferentMajorVersions2() {
		Version version1 = new Version("cppcheck 1.2.3");
		Version version2 = new Version("cppcheck 2.0.0");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumWithDifferentMajorVersions2() {
		Version version1 = new Version("cppcheck premium 1.2.3");
		Version version2 = new Version("cppcheck premium 2.0.0");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiuSafetyCertifiedmWithDifferentMajorVersions2() {
		Version version1 = new Version("cppcheck premium 1.2.3s");
		Version version2 = new Version("cppcheck premium 2.0.0s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanDifferentMinorVersions2() {
		Version version1 = new Version("cppcheck 1.2.6");
		Version version2 = new Version("cppcheck 1.3.0");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanDifferentMinorVersions3() {
		Version version1 = new Version("cppcheck 24.4.0");
		Version version2 = new Version("cppcheck 24.10.1");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumDifferentMinorVersions2() {
		Version version1 = new Version("cppcheck premium 1.2.6");
		Version version2 = new Version("cppcheck premium 1.3.0");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCertifiedDifferentMinorVersions2() {
		Version version1 = new Version("cppcheck premium 1.2.6s");
		Version version2 = new Version("cppcheck premium 1.3.0s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithDiffrentRevisionVersions() {
		Version version1 = new Version("cppcheck 1.2.3");
		Version version2 = new Version("cppcheck 1.2.4");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumWithDiffrentRevisionVersions() {
		Version version1 = new Version("cppcheck premium 1.2.3");
		Version version2 = new Version("cppcheck premium 1.2.4");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCertifiedWithDiffrentRevisionVersions() {
		Version version1 = new Version("cppcheck premium 1.2.3s");
		Version version2 = new Version("cppcheck premium 1.2.4s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithSameVersions() {
		Version version1 = new Version("cppcheck 1.2.3");
		Version version2 = new Version("cppcheck 1.2.3");
		assertFalse(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithDiffrentRevisionVersions2() {
		Version version1 = new Version("cppcheck 1.2.3.5");
		Version version2 = new Version("cppcheck 1.2.4.5");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithDifferentPatchVersions() {
		Version version1 = new Version("cppcheck 1.2.3.4");
		Version version2 = new Version("cppcheck 1.2.3.5");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanWithDefaultRevisionAndPatch() {
		Version version1 = new Version("cppcheck 1.2");
		Version version2 = new Version("cppcheck 1.2.3");
		Version version3 = new Version("cppcheck 1.2.0");
		Version version4 = new Version("cppcheck 1.2.0.1");
		assertTrue(version2.isGreaterThan(version1));
		assertTrue(version4.isGreaterThan(version1));
		assertFalse(version3.isGreaterThan(version1));
		assertTrue(version4.isGreaterThan(version3));
	}

	@Test
	public void testIsGreaterThanWithSamePatchVersions() {
		Version version1 = new Version("cppcheck 1.2.3.4");
		Version version2 = new Version("cppcheck 1.2.3.4");
		assertFalse(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumVersions() {
		Version version1 = new Version("cppcheck premium 1.2.3.4");
		Version version2 = new Version("cppcheck premium 1.2.3.5");
		Version version3 = new Version("cppcheck premium 1.2.3.5");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
		assertFalse(version2.isGreaterThan(version3));
		assertFalse(version3.isGreaterThan(version2));
	}

	@Test
	public void testIsGreaterThanPremiumSafetyCerifiedVersions() {
		Version version1 = new Version("cppcheck premium 1.2.3.4s");
		Version version2 = new Version("cppcheck premium 1.2.3.5s");
		Version version3 = new Version("cppcheck premium 1.2.3.5s");
		assertTrue(version2.isGreaterThan(version1));
		assertFalse(version1.isGreaterThan(version2));
		assertFalse(version2.isGreaterThan(version3));
		assertFalse(version3.isGreaterThan(version2));
	}

	@Test
	public void testCompatibility() {
		Version version1 = new Version("cppcheck 1.56");
		Version version2 = new Version("cppcheck 1.56.0");
		Version version3 = new Version("cppcheck 1.56.0.0");
		assertTrue(version1.isCompatible());
		assertTrue(version2.isCompatible());
		assertTrue(version3.isCompatible());
		version1 = new Version("cppcheck premium 1.56");
		version2 = new Version("cppcheck premium 1.56.0");
		version3 = new Version("cppcheck premium 1.56.0.0");
		assertTrue(version1.isCompatible());
		assertTrue(version2.isCompatible());
		assertTrue(version3.isCompatible());
		version1 = new Version("cppcheck premium 1.56s");
		version2 = new Version("cppcheck premium 1.56.0s");
		version3 = new Version("cppcheck premium 1.56.0.0s");
		assertTrue(version1.isCompatible());
		assertTrue(version2.isCompatible());
		assertTrue(version3.isCompatible());
	}

	@Test
	public void testPremium() {
		Version version1 = new Version("cppcheck 1.56");
		Version version2 = new Version("cppcheck 1.56.0");
		Version version3 = new Version("cppcheck 1.56.0.0");
		assertFalse(version1.isPremium());
		assertFalse(version2.isPremium());
		assertFalse(version3.isPremium());
		version1 = new Version("cppcheck premium 1.56");
		version2 = new Version("cppcheck premium 1.56.0");
		version3 = new Version("cppcheck premium 1.56.0.0");
		assertTrue(version1.isPremium());
		assertTrue(version2.isPremium());
		assertTrue(version3.isPremium());
		version1 = new Version("cppcheck premium 1.56s");
		version2 = new Version("cppcheck premium 1.56.0s");
		version3 = new Version("cppcheck premium 1.56.0.0s");
		assertTrue(version1.isPremium());
		assertTrue(version2.isPremium());
		assertTrue(version3.isPremium());
	}

}
