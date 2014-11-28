package org.webstories.release.utils;

public class ArrayUtils {
	public static String join( Object[] items, String delimiter ) {
		String result = "";
		for ( int i = 0; i < items.length; i++ ) {
			result += items[ i ];
			if ( i != items.length - 1 ) {
				result += delimiter;
			}
		}
		return result;
	}
}