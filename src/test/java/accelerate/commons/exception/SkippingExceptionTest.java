package accelerate.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 19, 2019
 */
@SuppressWarnings("static-method")
class SkippingExceptionTest {
	/**
	 * Test method for
	 * {@link accelerate.commons.exception.SkippingException#SkippingException(java.lang.String)}.
	 */
	@Test
	void testSkippingException() {
		assertEquals("testSkippingException", new SkippingException("testSkippingException").getMessage());
	}
}
