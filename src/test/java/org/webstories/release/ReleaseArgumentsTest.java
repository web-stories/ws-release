package org.webstories.release;

import org.junit.Assert;
import org.junit.Test;

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
	
	@Test
	public void should_consider_arguments_that_doesnt_have_prefix_as_task_declaration() {
		ReleaseArguments args = new ReleaseArguments(new String[] {
			"deploy"	
		});
		
		String actual = args.getDeclaredTasks().toString();
		String expected = "[deploy]";
		
		Assert.assertEquals( expected, actual );
	}
}
