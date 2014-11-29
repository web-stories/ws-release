package org.webstories.release.utils;

import java.io.IOException;
import java.net.Socket;

public class SocketUtils {
	public static boolean isPortAvailable( int port ) {
		try ( Socket socket = new Socket( "localhost", port ) ) {
			return false;
		} catch ( IOException e ) {
			return true;
		}
	}
}
