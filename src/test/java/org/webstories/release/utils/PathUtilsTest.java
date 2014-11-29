package org.webstories.release.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class PathUtilsTest {
	@Test
	public void should_get_the_file_that_starts_with_the_given_prefix() throws IOException {
		Path directory = Paths.get( "src/test/resources" );
		Path file = PathUtils.getFileThatStartsWith( "prefixed-", directory );
		
		if ( file == null ) {
			Assert.fail( "method should not return null" );
			return;
		}
		
		String actual = file.getFileName().toString();
		String expected = "prefixed-file";
		
		Assert.assertEquals( expected, actual );
	}
	
	@Test
	public void should_get_the_full_path_that_starts_with_the_given_prefix() throws IOException {
		Path directory = Paths.get( "src/test/resources/parent" );
		Path file = PathUtils.getFileThatStartsWith( "prefixed-", directory );
		
		if ( file == null ) {
			Assert.fail( "method should not return null" );
			return;
		}
		
		String actual = file.toString();
		String expected = Paths.get( "src/test/resources/parent/prefixed-file" ).toString();
		
		Assert.assertEquals( expected, actual );
	}
}
