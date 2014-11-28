package org.webstories.release.build;


public class BuildTasks {
	private SynchronousCommand mvn = new SynchronousCommand( "mvn" );
	
	public void doBuild() throws CommandException {
		mvn.execute( "install" );
	}
}
