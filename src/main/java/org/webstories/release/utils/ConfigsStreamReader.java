package org.webstories.release.utils;

import java.io.IOException;
import java.io.InputStream;

public class ConfigsStreamReader {
	public void read( InputStreamAction action ) throws InputStreamException {
		InputStream configsStream = getClass().getResourceAsStream( "/configs.properties" );
		
		if ( configsStream == null ) {
			throw new InputStreamException( "File 'configs.properties' not found in the classpath" );
		}
		
		try {
			action.run( configsStream );
		} catch ( Exception e ) {
			throw new InputStreamException( e );
		} finally {
			try {
				configsStream.close();
			} catch ( IOException e ) {
				throw new InputStreamException( e );
			}
		}
	}
}
