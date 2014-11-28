package org.webstories.release.git;

import java.io.IOException;

import org.eclipse.jgit.api.Git;


public class GitCommands {
	private Git git;
	
	public GitCommands( Git git ) {
		this.git = git;
	}
	
	public boolean isBranch( String name ) throws CommandException {
		try {
			String currentBranchName = git.getRepository().getFullBranch();
			return ( "refs/heads/" + name ).equals( currentBranchName );
		} catch ( IOException e ) {
			throw new CommandException( e );
		}
	}
}
