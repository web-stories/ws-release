package org.webstories.release.command;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

public class SynchronousCommand extends Command {
	private Map<String, String> env = new HashMap<String, String>( System.getenv() );
	
	public SynchronousCommand( String name ) {
		super( name );
	}
	
	public SynchronousCommand( String name, File cwd ) {
		super( name, cwd );
	}
	
	public void addEnv( String key, String value ) {
		env.put( key, value );
	}
	
	@Override
	protected SyncCommandResult execute( Executor executor, String... arguments )
	throws CommandException {
		CommandLine command = new CommandLine( "cmd" );
		command.addArgument( "/c" );
		command.addArgument( name );
		
		for ( String argument : arguments ) {
			command.addArgument( argument );
		}
		
		LineLogOutputStream stdout = new LineLogOutputStream();
		executor.setStreamHandler( new PumpStreamHandler( stdout, System.err, System.in ) );
		
		try {
			int exitCode = executor.execute( command, env );
			List<String> lines = stdout.getLines();
			
			if ( executor.isFailure( exitCode ) ) {
				throw new CommandException( name, arguments );
			}
			
			return new SyncCommandResult( name, arguments, exitCode, lines );
		} catch ( IOException e ) {
			throw new CommandException( e );
		}
	}
}