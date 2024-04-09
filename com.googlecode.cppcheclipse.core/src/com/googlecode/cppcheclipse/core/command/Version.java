package com.googlecode.cppcheclipse.core.command;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Version {

	public enum VersionType {
		OPEN_SOURCE,
		PREMIUM,
		PREMIUM_SAFETY_CERTIFIED,
		ANY
	}

	private static final String VERSION_PREFIX = "cppcheck";
	private static final String VERSION_PREMIUM = "premium";
	private static final String SAFETY_CERTIFIED_MARK = "s";
	private static final String DELIMITER = ".";
	private final int majorVersion;
	private final int minorVersion;
	private final int revision;
	private final int patch;
	private final VersionType versionType;

	public static final Version MIN_VERSION = new Version (1, 56, 0, 0, VersionType.ANY);

	private Version(int majorVersion, int minorVersion, int revision, int patch, VersionType versionType) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.revision = revision;
		this.patch = patch;
		this.versionType = versionType;
	}

	/** version string must have the format
	 * "cppcheck [premium] <majorVersion>.<minorVersion>[.<revision>][.patch][s]"
	 *
	 * @param version
	 */
	public Version(String version) {
		version = version.toLowerCase();

		if (!version.startsWith(VERSION_PREFIX)) {
			throw new IllegalArgumentException("Version must start with " + VERSION_PREFIX + ", but is " + version);
		}

		version = version.substring(VERSION_PREFIX.length()).trim();

		if (version.contains(VERSION_PREMIUM) && version.endsWith(SAFETY_CERTIFIED_MARK)) {
			versionType = VersionType.PREMIUM_SAFETY_CERTIFIED;
			version = version.substring(VERSION_PREMIUM.length()).trim();
		} else if (version.contains(VERSION_PREMIUM)) {
			versionType = VersionType.PREMIUM;
			version = version.substring(VERSION_PREMIUM.length()).trim();
		} else {
			versionType = VersionType.OPEN_SOURCE;
		}

		// extract the version with the delimiter from the string
		version = version.replaceAll("[^0-9" + DELIMITER + "]","");

		try {
			StringTokenizer tokenizer = new StringTokenizer(version, DELIMITER);
			String versionPart = tokenizer.nextToken();
			majorVersion = Integer.parseInt(versionPart);
			versionPart = tokenizer.nextToken();
			minorVersion = Integer.parseInt(versionPart);
			revision = tokenizer.hasMoreTokens() ? Integer.parseInt(tokenizer.nextToken()) : 0;
			patch = tokenizer.hasMoreTokens() ? Integer.parseInt(tokenizer.nextToken()) : 0;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Version must consist of at least two integers, separated by " + DELIMITER);
		}
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getRevision() {
		return revision;
	}

	public int getPatch() {
		return patch;
	}

	public VersionType  getType() {
		return versionType;
	}

	/**
	 *
	 * @param version
	 * @return true if the given version is greater than the current version
	 */
	public boolean isGreaterThan(Version version) {
		if (version.versionType != VersionType.ANY && versionType != version.versionType) {
			throw new IllegalArgumentException("Cannot compare versions of different types");
		}
		if (majorVersion != version.majorVersion)
			return majorVersion > version.majorVersion;
		if (minorVersion != version.minorVersion)
			return minorVersion > version.minorVersion;
		if (revision != version.revision)
			return revision > version.revision;
		return patch > version.patch;
	}


	public boolean isGreaterOrEqual(Version version) {
		return isGreaterThan(version) || equals(version);
	}

	/**
	 * minimum required version check
	 * @return true, if current version is greater or equal to the minimum required version.
	 */
	public boolean isCompatible() {
		return isGreaterOrEqual(MIN_VERSION);
	}


	/**
	 * @return true if the version is a premium version
	 */
	public boolean isPremium() {
		return getType() == VersionType.PREMIUM || getType() == VersionType.PREMIUM_SAFETY_CERTIFIED;
	}


	@Override
	public String toString() {
		StringBuffer version = new StringBuffer();
		version.append(versionType.toString().toLowerCase()).append(" ");
		version.append(majorVersion).append(".").append(minorVersion);
		version.append(".").append(revision);
		version.append(".").append(patch);
		return version.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + majorVersion;
		result = prime * result + minorVersion;
		result = prime * result + revision;
		result = prime * result + patch;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (majorVersion != other.majorVersion)
			return false;
		if (minorVersion != other.minorVersion)
			return false;
		if (revision != other.revision)
			return false;
		if (patch != other.patch)
			return false;
		return true;
	}
	
}
