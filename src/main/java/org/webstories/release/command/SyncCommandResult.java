package org.webstories.release.command;

import java.util.List;

public class SyncCommandResult extends CommandResult {
	private int exitCode;
	private List<String> lines;
	
	protected SyncCommandResult( String command, String[] args, int exitCode, List<String> lines ) {
		super( command, args );
		this.exitCode = exitCode;
		this.lines = lines;
	}
	
	protected int getExitCode() {
		return exitCode;
	}
	protected List<String> getLines() {
		return lines;
	}
}
