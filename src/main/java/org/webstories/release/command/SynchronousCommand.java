package org.webstories.release.command;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

public class SynchronousCommand {
	private String name;
	private File cwd;
	private Map<String, String> env = new HashMap<String, String>( System.getenv() );
	
	public SynchronousCommand( String name ) {
		this.name = name;
	}
	
	public SynchronousCommand( String name, File cwd ) {
		this.name = name;
		this.cwd = cwd;
	}
	
	public void addEnv( String key, String value ) {
		env.put( key, value );
	}
	
	public class CommandResult {
		private int exitCode;
		private List<String> lines;
		private String command;
		private String[] arguments;
		protected CommandResult( String command, String[] args, int exitCode, List<String> lines ) {
			this.command = command;
			this.arguments = args;
			this.exitCode = exitCode;
			this.lines = lines;
		}
		protected String getCommand() {
			return command;
		}
		protected String[] getArguments() {
			return arguments;
		}
		protected int getExitCode() {
			return exitCode;
		}
		protected List<String> getLines() {
			return lines;
		}
	}
	
	public CommandResult execute( String... arguments )
	throws CommandException {
		CommandLine command = new CommandLine( "cmd" );
		command.addArgument( "/c" );
		command.addArgument( name );
		
		for ( String argument : arguments ) {
			command.addArgument( argument );
		}
		
		LineLogOutputStream stdout = new LineLogOutputStream();
		DefaultExecutor executor = new DefaultExecutor();
		
		if ( cwd != null ) {
			executor.setWorkingDirectory( cwd );
		}
		
		executor.setStreamHandler( new PumpStreamHandler( stdout, System.err, System.in ) );
		
		try {
			int exitCode = executor.execute( command, env );
			List<String> lines = stdout.getLines();
			
			if ( executor.isFailure( exitCode ) ) {
				throw new CommandException( name, arguments );
			}
			
			return new CommandResult( name, arguments, exitCode, lines );
		} catch ( IOException e ) {
			throw new CommandException( e );
		}
	}
}