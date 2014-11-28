package org.webstories.release;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webstories.release.command.BuildTasks;
import org.webstories.release.command.CommandException;
import org.webstories.release.git.GitCommands;
import org.webstories.release.git.GitException;
import org.webstories.release.utils.ConfigsStreamReader;
import org.webstories.release.utils.InputStreamAction;
import org.webstories.release.utils.InputStreamException;


public class Main {
	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main( String[] args ) {
		try {
			String password = getConfig( "ssh.password" );
			GitCommands commands = GitCommands.create( password );
			BuildTasks buildTasks = new BuildTasks();
			
			logger.info( "Checking if current branch is 'master'..." );
			if( !commands.isBranch( "master" ) ) {
				throw new ReleaseException( "The current checked out branch should be 'master'" );
			}
			
			logger.info( "Synchronizing local repository with remotes..." );
			commands.update();
			
			logger.info( "Executing build..." );
			buildTasks.doBuild();
			
			// TODO
			
			logger.info( "All done!" );
		} catch ( ReleaseException | GitException | CommandException e ) {
			System.out.println( e.getMessage() );
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
