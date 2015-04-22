package org.webstories.release;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.webstories.release.server.JBossServerTasks;
import org.webstories.release.utils.Logger;

import com.fagnerbrack.release.ArgumentNotFoundException;
import com.fagnerbrack.release.ProjectVersion;
import com.fagnerbrack.release.ReleaseArguments;
import com.fagnerbrack.release.ReleaseException;
import com.fagnerbrack.release.build.BuildException;
import com.fagnerbrack.release.build.BuildTasks;
import com.fagnerbrack.release.build.MavenBuildTasks;
import com.fagnerbrack.release.git.GitCommands;
import com.fagnerbrack.release.git.GitException;
import com.fagnerbrack.release.server.DeploymentException;


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
				BuildTasks buildTasks = new MavenBuildTasks();
				Logger.task( "Executing build..." );
				buildTasks.executeFullBuild( "-P", "prod", "-Dmaven.test.skip=true" );
			}
			
			if ( declaredTasks.contains( "deploy" ) ) {
				if ( !arguments.contains( "jboss" ) ) {
					throw new ReleaseException( "'jboss' argument not found" );
				}
				if ( !arguments.contains( "version" ) ) {
					throw new ReleaseException( "'version' argument not found" );
				}
				JBossServerTasks serverTasks = JBossServerTasks.create(
					Paths.get( arguments.getValue( "jboss" ) ),
					ProjectVersion.create( arguments.getValue( "version" ) )
				);
				Logger.task( "Deploying artifact..." );
				serverTasks.deploy();
			}
			
			Logger.task( "All done!" );
		} catch ( ReleaseException | GitException | BuildException | ArgumentNotFoundException |
		DeploymentException e ) {
			Logger.error( e.getMessage() );
		}
	}
	
	private static String getConfig( String key ) throws ReleaseException {
		final Properties properties = new Properties();
		InputStream configsStream = Main.class.getResourceAsStream( "/configs.properties" );
		
		if ( configsStream == null ) {
			throw new ReleaseException( "config file not found" );
		}
		
		try {
			properties.load( configsStream );
		} catch ( IOException e ) {
			throw new ReleaseException( e );
		}
		
		String result = properties.getProperty( key );
		
		if ( result == null ) {
			throw new ReleaseException( "config not found: " + key );
		}
		
		return result;
	}
}
