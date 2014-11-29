package org.webstories.release;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.webstories.release.build.BuildTasks;
import org.webstories.release.build.CommandException;
import org.webstories.release.git.GitCommands;
import org.webstories.release.git.GitException;
import org.webstories.release.server.DeploymentException;
import org.webstories.release.server.ServerTasks;
import org.webstories.release.utils.ConfigsStreamReader;
import org.webstories.release.utils.InputStreamAction;
import org.webstories.release.utils.InputStreamException;
import org.webstories.release.utils.Logger;


public class Main {
	public static void main( String[] args ) {
		ReleaseArguments arguments = new ReleaseArguments( args );
		List<String> declaredTasks = arguments.getDeclaredTasks();
		
		// If no task was declared, then execute all of them
		if ( declaredTasks.isEmpty() ) {
			declaredTasks.add( "git" );
			declaredTasks.add( "build" );
			declaredTasks.add( "deploy" );
		}
		
		try {
			if ( declaredTasks.contains( "git" ) ) {
				String password = getConfig( "ssh.password" );
				GitCommands gitCommands = GitCommands.create( password );
				Logger.task( "Checking if current branch is 'master'..." );
				if( !gitCommands.isBranch( "master" ) ) {
					throw new ReleaseException( "The current checked out branch should be 'master'" );
				}
				Logger.task( "Synchronizing local repository with remotes..." );
				gitCommands.update();
			}
			
			if ( declaredTasks.contains( "build" ) ) {
				BuildTasks buildTasks = BuildTasks.create();
				Logger.task( "Executing build..." );
				buildTasks.doBuild();
			}
			
			if ( declaredTasks.contains( "deploy" ) ) {
				if ( !arguments.contains( "jboss" ) ) {
					throw new ReleaseException( "'jboss' argument not found" );
				}
				if ( !arguments.contains( "version" ) ) {
					throw new ReleaseException( "'version' argument not found" );
				}
				ServerTasks serverTasks = ServerTasks.create(
					Paths.get( arguments.getValue( "jboss" ) ),
					ProjectVersion.create( arguments.getValue( "version" ) )
				);
				Logger.task( "Deploying artifact..." );
				serverTasks.deploy();
			}
			
			Logger.task( "All done!" );
		} catch ( ReleaseException | GitException | CommandException |
		ArgumentNotFoundException | DeploymentException e ) {
			Logger.error( e.getMessage() );
		}
	}
	
	private static String getConfig( String key ) throws ReleaseException {
		final Properties properties = new Properties();
		ConfigsStreamReader configsReader = new ConfigsStreamReader();
		
		try {
			configsReader.read(new InputStreamAction() {
				@Override
				public void run( InputStream input ) throws IOException {
					properties.load( input );
				}
			});
		} catch ( InputStreamException e ) {
			throw new ReleaseException( e );
		}
		
		return properties.getProperty( key );
	}
}
