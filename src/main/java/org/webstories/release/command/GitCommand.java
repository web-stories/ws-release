package org.webstories.release.command;


public class GitCommand extends SynchronousCommand {
	public boolean isBranch( String name ) throws CommandException {
		CommandResult result = git( "branch" );
		for ( String line : result.getLines() ) {
			if ( "* master".equals( line ) ) {
				return true;
			}
		}
		return false;
	}
	
	private CommandResult git( String... arguments ) throws CommandException {
		return executeCommand( "git", arguments );
	}
}
