package org.webstories;

import org.junit.Assert;
import org.junit.Test;
import org.webstories.release.ArgumentNotFoundException;
import org.webstories.release.ReleaseArguments;

public class ReleaseArgumentsTest {
	@Test
	public void should_get_argument_with_value() throws ArgumentNotFoundException {
		ReleaseArguments args = new ReleaseArguments(new String[] {
			"--jboss=\"c:/jboss\""	
		});
		
		String actual = args.getValue( "jboss" );
		String expected = "c:/jboss";
		
		Assert.assertEquals( expected, actual );
	}
}
