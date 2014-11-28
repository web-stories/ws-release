package org.webstories.release.build;

import java.io.IOException;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

public class SynchronousCommand {
	private String name;
	
	protected SynchronousCommand( String name ) {
		this.name = name;
	}
	
	protected class CommandResult {
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
	
	protected CommandResult execute( String... arguments )
	throws CommandException {
		CommandLine command = new CommandLine( "cmd" );
		command.addArgument( "/c" );
		command.addArgument( name );
		
		for ( String argument : arguments ) {
			command.addArgument( argument );
		}
		
		LineLogOutputStream stdout = new LineLogOutputStream();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setStreamHandler( new PumpStreamHandler( stdout, System.err, System.in ) );
		
		try {
			int exitCode = executor.execute( command );
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