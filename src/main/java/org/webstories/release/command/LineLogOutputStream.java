package org.webstories.release.command;

import java.util.ArrayList;

import org.apache.commons.exec.LogOutputStream;

public class LineLogOutputStream extends LogOutputStream {
	private ArrayList<String> lines = new ArrayList<String>();
	
	@Override
	protected void processLine( String line, int logLevel ) {
		lines.add( line );
	}
	
	public ArrayList<String> getLines() {
		return lines;
	}
}
