package org.webstories.release.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.webstories.release.ProjectVersion;
import org.webstories.release.command.CommandException;
import org.webstories.release.command.SynchronousCommand;
import org.webstories.release.utils.PathUtils;

public class ServerTasks {
	private Path jbossHome;
	private ProjectVersion version;
	
	private ServerTasks( Path jbossHome, ProjectVersion version ) {
		this.jbossHome = jbossHome;
		this.version = version;
	}
	
	public static ServerTasks create( Path jbossHome, ProjectVersion version ) {
		return new ServerTasks( jbossHome, version );
	}
	
	/**
	 * Deploy the artifact generated by the last build process, stopping or restarting the server
	 * if necessary.
	 */
	public void deploy() throws DeploymentException {
		String prefix = "WebStories-" + version;
		Path directory = Paths.get( "target" );
		Path artifactPath = null;
		
		try {
			artifactPath = PathUtils.getFileThatStartsWith( prefix, directory );
		} catch ( IOException e ) {
			throw new DeploymentException( e );
		}
		
		if ( artifactPath == null ) {
			throw new DeploymentException(
				"Failed to find the '.war' file to deploy inside the 'target' directory"
			);
		}
		
		Path deploymentsDir = jbossHome.resolve( "standalone/deployments/" );
		Path binDir = jbossHome.resolve( "bin" );
		Path deploymentTarget = deploymentsDir.resolve( artifactPath.getFileName() );
		
		if ( Files.exists( deploymentTarget ) ) {
			throw new DeploymentException( "The artifact is already deployed" );
		}
		
		try {
			File cwd = binDir.toFile();
			SynchronousCommand jbossCli = new SynchronousCommand( "jboss-cli", cwd );
			jbossCli.execute( "--connect", "command=:shutdown" );
		} catch ( CommandException e ) {
			throw new DeploymentException( e );
		}
		
		try {
			FileUtils.cleanDirectory( deploymentsDir.toFile() );
			Files.copy( artifactPath, deploymentTarget );
		} catch ( IOException e ) {
			throw new DeploymentException( e );
		}
		
		// TODO start the server
	}
}
