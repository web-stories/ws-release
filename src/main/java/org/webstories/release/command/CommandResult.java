package org.webstories.release.command;


public class CommandResult {
	private String command;
	private String[] arguments;
	protected CommandResult( String command, String[] args ) {
		this.command = command;
		this.arguments = args;
	}
	protected String getCommand() {
		return command;
	}
	protected String[] getArguments() {
		return arguments;
	}
}