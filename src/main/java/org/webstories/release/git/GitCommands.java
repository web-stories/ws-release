package org.webstories.release.git;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProviderUserInfo;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.webstories.release.ReleaseException;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class GitCommands {
	private Git git;
	
	private GitCommands( Git git ) {
		this.git = git;
	}
	
	public static GitCommands create( String password ) throws ReleaseException {
		setPassword( password );
		
		try {
			Repository repository = new RepositoryBuilder()
				.readEnvironment() // Scan environment GIT_* variables
				.findGitDir() // Scan up the file system tree
				.build();
				
			Git git = new Git( repository );
			
			return new GitCommands( git );
		} catch ( IOException e ) {
			throw new ReleaseException( e );
		}
	}
	
	public static void setPassword( final String password ) {
		JschConfigSessionFactory sessionFactory = new JschConfigSessionFactory() {
			@Override
			protected void configure( Host hc, Session session ) {
				CredentialsProvider provider = new CredentialsProvider() {
					@Override
					public boolean supports( CredentialItem... items ) {
						return true;
					}
					@Override
					public boolean isInteractive() {
						return false;
					}
					@Override
					public boolean get( URIish uri, CredentialItem... items )
					throws UnsupportedCredentialItem {
						for ( CredentialItem item : items ) {
							if ( item instanceof CredentialItem.StringType ) {
								( ( CredentialItem.StringType )item ).setValue( password );
							}
						}
						return true;
					}
				};
				UserInfo userInfo = new CredentialsProviderUserInfo( session, provider );
				session.setUserInfo( userInfo );
			}
		};
		SshSessionFactory.setInstance( sessionFactory );
	}
	
	public boolean isBranch( String name ) throws GitException {
		try {
			String currentBranchName = git.getRepository().getFullBranch();
			return ( "refs/heads/" + name ).equals( currentBranchName );
		} catch ( IOException e ) {
			throw new GitException( e );
		}
	}
	
	public void update() throws GitException {
		try {
			PullResult result = git.pull().call();
			if ( !result.isSuccessful() ) {
				throw new GitException( "Failed to pull changes" );
			}
		} catch ( GitAPIException e ) {
			throw new GitException( e );
		}
	}
}
