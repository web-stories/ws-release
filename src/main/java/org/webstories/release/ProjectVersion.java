package org.webstories.release;

import java.util.regex.Pattern;

public class ProjectVersion {
	private int major;
	private int minor;
	private int patch;
	
	private ProjectVersion( int major, int minor, int patch ) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	/**
	 * @param  version
	 *         A version input in the format x.x.x, where "x" is a number from 0-9
	 */
	public static ProjectVersion create( String version ) {
		String[] parts = version.split( Pattern.quote( "." ) );
		int major = Integer.parseInt( parts[ 0 ] );
		int minor = Integer.parseInt( parts[ 1 ] );
		int patch = Integer.parseInt( parts[ 2 ] );
		return new ProjectVersion( major, minor, patch );
	}
	
	@Override
	public String toString() {
		return major + "." + minor + "." + patch;
	}
}