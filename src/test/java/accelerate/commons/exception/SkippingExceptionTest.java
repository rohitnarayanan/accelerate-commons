package accelerate.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link SkippingException}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 19, 2019
 */
@SuppressWarnings("static-method")
class SkippingExceptionTest {
	/**
	 * Test method for {@link SkippingException#SkippingException(String)}.
	 */
	@Test
	void testSkippingException() {
		assertEquals("testSkippingException", new SkippingException("testSkippingException").getMessage());
	}
}
