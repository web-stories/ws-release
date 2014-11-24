package org.webstories.release.command;

import org.webstories.release.utils.ArrayUtils;

public class CommandException extends Exception {
	private static final long serialVersionUID = 1;
	public CommandException( String command, String[] arguments ) {
		super(
			ArrayUtils.join( new String[] {
				"Failed to run '$ ",
				command,
				ArrayUtils.join( arguments , " " ),
				"' command."
			}, " " )
		);
	}
	public CommandException( String msg ) {
		super( msg );
	}
	public CommandException( Throwable cause ) {
		super( cause );
	}
}
