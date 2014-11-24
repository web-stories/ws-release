package org.webstories.release;

import org.webstories.release.command.CommandException;
import org.webstories.release.command.GitCommand;


public class Main {
	public static void main( String[] args ) {
		GitCommand git = new GitCommand();
		
		try {
			if ( !git.isBranch( "master" ) ) {
				throw new CommandException( "Current branch is not master" );
			}
			// TODO
		} catch ( CommandException e ) {
			System.out.println( e.getMessage() );
		}
	}
}
