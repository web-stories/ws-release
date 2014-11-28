package org.webstories.utils;

import java.io.IOException;
import java.io.InputStream;

public class ConfigsStreamReader {
	public void read( InputStreamAction action ) throws InputStreamException {
		InputStream in = getClass().getResourceAsStream( "/configs.properties" );
		try {
			action.run( in );
		} catch ( Exception e ) {
			throw new InputStreamException( e );
		} finally {
			try {
				in.close();
			} catch ( IOException e ) {
				throw new InputStreamException( e );
			}
		}
	}
}
