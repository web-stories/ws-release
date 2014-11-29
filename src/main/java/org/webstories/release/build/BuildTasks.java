package org.webstories.release.build;

import org.webstories.release.command.CommandException;
import org.webstories.release.command.SynchronousCommand;


public class BuildTasks {
	private SynchronousCommand mvn = new SynchronousCommand( "mvn" );
	
	private BuildTasks() {}
	
	public static BuildTasks create() {
		return new BuildTasks();
	}
	
	public void doBuild() throws BuildException {
		try {
			mvn.execute( "install" );
		} catch ( CommandException e ) {
			throw new BuildException( e );
		}
	}
}
