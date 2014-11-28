package org.webstories.release.git;

import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.webstories.release.ReleaseException;

public class RepositoryUtils {
	public static Repository create() throws ReleaseException {
		try {
			return new RepositoryBuilder()
				.readEnvironment() // Scan environment GIT_* variables
				.findGitDir() // Scan up the file system tree
				.build();
		} catch ( IOException e ) {
			throw new ReleaseException( e );
		}
	}
}
