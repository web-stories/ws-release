package org.webstories.release;

public class ReleaseException extends Exception {
	private static final long serialVersionUID = 1;
	public ReleaseException( String msg ) {
		super( msg );
	}
	public ReleaseException( Throwable cause ) {
		super( cause );
	}
}
