package org.webstories.release.git;


public class GitException extends Exception {
	private static final long serialVersionUID = 1;
	public GitException( String msg ) {
		super( msg );
	}
	public GitException( Throwable cause ) {
		super( cause );
	}
}
