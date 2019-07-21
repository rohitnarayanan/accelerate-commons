package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link RuntimeUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class RuntimeUtilsTests {
	/**
	 * Test method for
	 * {@link RuntimeUtils#executeOSCommand(String, String[], java.io.File)}.
	 */
	@Test
	void testExecuteOSCommand() {
		assertEquals("testExecuteOSCommand", RuntimeUtils.executeOSCommand("echo testExecuteOSCommand", null, null));

		assertEquals("testExecuteOSCommand", RuntimeUtils.executeOSCommand("echo testExecuteOSCommand", null,
				new File(System.getProperty("user.home"))));
	}
}