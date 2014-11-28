package org.webstories.release.utils;

public class Logger {
	public static void task( String msg ) {
		System.out.println( "RELEASE: " + msg );
	}
	public static void error( String msg ) {
		System.out.println( "RELEASE FAILURE: " + msg );
	}
}
