package org.webstories.release.command;

import java.io.IOException;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webstories.release.utils.ArrayUtils;

public abstract class SynchronousCommand {
	private Logger logger = LoggerFactory.getLogger( SynchronousCommand.class );
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
	protected CommandResult executeCommand( String name, String[] arguments )
	throws CommandException {
		CommandLine command = new CommandLine( name );
		
		for ( String argument : arguments ) {
			command.addArgument( argument );
		}
		
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		LineLogOutputStream stdout = new LineLogOutputStream();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setStreamHandler( new PumpStreamHandler( stdout, System.err, System.in ) );
		
		try {
			
			debugCommand( "Executing command '{}'", name, arguments );
			
			executor.execute( command, resultHandler );
			resultHandler.waitFor();
			
			debugCommand( "Command '{}' executed", name, arguments );
			
			int exitCode = resultHandler.getExitValue();
			List<String> lines = stdout.getLines();
			
			if ( executor.isFailure( exitCode ) ) {
				throw new CommandException( name, arguments );
			}
			
			return new CommandResult( name, arguments, exitCode, lines );
		} catch ( IOException | InterruptedException e ) {
			throw new CommandException( e );
		}
	}
	
	private void debugCommand( String msg, String commandName, String[] args ) {
		String command = commandName + " " + ArrayUtils.join( args , " " );
		msg = msg.replace( "{}", command );
		logger.debug( msg );
	}
}
