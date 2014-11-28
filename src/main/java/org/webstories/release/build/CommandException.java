package org.webstories.release.build;

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
	public CommandException( Throwable cause ) {
		super( cause );
	}
}