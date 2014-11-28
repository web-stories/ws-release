package org.webstories.release.git;


public class CommandException extends Exception {
	private static final long serialVersionUID = 1;
	public CommandException( String msg ) {
		super( msg );
	}
	public CommandException( Throwable cause ) {
		super( cause );
	}
}
