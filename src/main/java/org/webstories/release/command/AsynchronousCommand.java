package org.webstories.release.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.Executor;

public class AsynchronousCommand extends Command {
	public AsynchronousCommand( String name, File cwd ) {
		super( name, cwd );
	}
	
	@Override
	protected CommandResult execute( Executor executor, String... arguments )
	throws CommandException {
		CommandLine command = new CommandLine( "cmd" );
		command.addArgument( "/c" );
		command.addArgument( "start" );
		command.addArgument( name );
		
		for ( String argument : arguments ) {
			command.addArgument( argument );
		}
		
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		
		try {
			executor.execute( command, resultHandler );
			return new CommandResult( name, arguments );
		} catch ( IOException e ) {
			throw new CommandException( e );
		}
	}
}
