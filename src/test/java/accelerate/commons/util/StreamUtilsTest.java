package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import accelerate.commons.exception.ApplicationException;

/**
 * {@link Test} class for {@link StreamUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 20, 2019
 */
@SuppressWarnings("static-method")
class StreamUtilsTest {
	/**
	 * Test method for {@link StreamUtils#loadInputStream(String, Function)}. Main
	 * funcationailty tested as part of {@link XMLUtilsTests#testLoadXML()}
	 */
	@Test
	void testLoadInputStream() {
		assertThrows(ApplicationException.class,
				() -> StreamUtils.loadInputStream("/invalid", aInputStream -> aInputStream.toString()));
		assertThrows(ApplicationException.class,
				() -> StreamUtils.loadInputStream("http://invalid", aInputStream -> aInputStream.toString()));
	}

	/**
	 * Test method for {@link StreamUtils#readInputStream(String)}. Tested as part
	 * of {@link XMLUtilsTests#testStringToXML()}
	 */
	@Test
	void testReadInputStream() {
		assertTrue(true);
	}
}