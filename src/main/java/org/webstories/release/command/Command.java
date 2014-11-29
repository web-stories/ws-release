package org.webstories.release.command;

import java.io.File;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;

public abstract class Command {
	protected String name;
	protected File cwd;
	
	public Command( String name ) {
		this.name = name;
	}
	
	public Command( String name, File cwd ) {
		this.name = name;
		this.cwd = cwd;
	}
	
	public CommandResult execute( String... arguments ) throws CommandException {
		Executor executor = new DefaultExecutor();
		
		if ( cwd != null ) {
			executor.setWorkingDirectory( cwd );
		}
		
		return execute( executor, arguments );
	}
	
	protected abstract CommandResult execute( Executor executor, String... arguments )
		throws CommandException;
}
