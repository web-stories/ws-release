package org.webstories.release.build;


public class BuildTasks {
	private SynchronousCommand mvn = new SynchronousCommand( "mvn" );
	
	private BuildTasks() {}
	
	public static BuildTasks create() {
		return new BuildTasks();
	}
	
	public void doBuild() throws CommandException {
		mvn.execute( "install" );
	}
}
