package org.webstories.release.utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jdt.annotation.Nullable;

public class PathUtils {
	public static @Nullable Path getFileThatStartsWith( String prefix, Path directory )
	throws IOException {
		Path result = null;
		try ( DirectoryStream<Path> items = Files.newDirectoryStream( directory ) ) {
			Path targetPrefix = Paths.get( prefix );
			for ( Path item : items ) {
				Path filename = item.getFileName();
				if ( filename.startsWith( targetPrefix ) ) {
					result = filename;
				}
			}
		}
		return result;
	}
}
