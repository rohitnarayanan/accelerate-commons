package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import accelerate.commons.exception.ApplicationException;

/**
 * {@link Test} class for {@link ConfigurationUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class ConfigurationUtilsTests {
	/**
	 * Test method for {@link ConfigurationUtils#loadPropertyFile(String)}.
	 */
	@Test
	void testLoadPropertyFile() {
		// test null input
		Assertions.assertThrows(ApplicationException.class, () -> ConfigurationUtils.loadPropertyFile(null));

		assertEquals("ConfigurationUtils",
				ConfigurationUtils.loadPropertyFile("/accelerate/commons/util/ConfigurationUtilsTest.properties")
						.get("accelerate.commons.util.ConfigurationUtils"));
	}

	/**
	 * Test method for {@link ConfigurationUtils#loadYAMLFile(String)}.
	 */
	@Test
	void testLoadYAMLFile() {
		// test null input
		Assertions.assertThrows(ApplicationException.class, () -> ConfigurationUtils.loadYAMLFile(null));

		assertEquals("ConfigurationUtils",
				ConfigurationUtils.loadYAMLFile("/accelerate/commons/util/ConfigurationUtilsTest.yml")
						.get("accelerate.commons.util.ConfigurationUtils"));
	}
}