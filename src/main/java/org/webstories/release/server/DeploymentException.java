package org.webstories.release.server;

public class DeploymentException extends Exception {
	private static final long serialVersionUID = 1;
	public DeploymentException( String msg ) {
		super( msg );
	}
	public DeploymentException( Throwable e ) {
		super( e );
	}
}
