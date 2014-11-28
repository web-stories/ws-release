package org.webstories.release;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.webstories.release.command.BuildTasks;
import org.webstories.release.command.CommandException;
import org.webstories.release.git.GitCommands;
import org.webstories.release.git.GitException;
import org.webstories.release.utils.ConfigsStreamReader;
import org.webstories.release.utils.InputStreamAction;
import org.webstories.release.utils.InputStreamException;
import org.webstories.release.utils.Logger;


public class Main {
	public static void main( String[] args ) {
		try {
			String password = getConfig( "ssh.password" );
			GitCommands commands = GitCommands.create( password );
			BuildTasks buildTasks = new BuildTasks();
			
			Logger.task( "Checking if current branch is 'master'..." );
			if( !commands.isBranch( "master" ) ) {
				throw new ReleaseException( "The current checked out branch should be 'master'" );
			}
			
			Logger.task( "Synchronizing local repository with remotes..." );
			commands.update();
			
			Logger.task( "Executing build..." );
			buildTasks.doBuild();
			
			// TODO
			
			Logger.task( "All done!" );
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
