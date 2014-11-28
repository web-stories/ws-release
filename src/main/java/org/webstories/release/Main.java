package org.webstories.release;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.webstories.release.git.CommandException;
import org.webstories.release.git.GitCommands;
import org.webstories.release.git.RepositoryUtils;


public class Main {
	public static void main( String[] args ) {
		try {
			Repository respository = RepositoryUtils.create();
			Git git = new Git( respository );
			GitCommands commands = new GitCommands( git );
			
			if( !commands.isBranch( "master" ) ) {
				throw new ReleaseException( "Current branch is not master" );
			}
			
			// TODO
		} catch ( ReleaseException | CommandException e ) {
			System.out.println( e.getMessage() );
		}
	}
}
