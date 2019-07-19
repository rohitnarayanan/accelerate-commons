package accelerate.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 19, 2019
 */
@SuppressWarnings("static-method")
class ApplicationExceptionTest {
	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException()}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.String)}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.String, java.lang.Throwable)}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.Throwable)}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.Throwable, java.lang.String, java.lang.Object[])}
	 */
	@Test
	void testApplicationException() {
		assertNotNull(new ApplicationException());
		assertEquals("testApplicationException", new ApplicationException("testApplicationException").getMessage());
		assertEquals("testNullPointerException", new ApplicationException("testApplicationException",
				new NullPointerException("testNullPointerException")).getCause().getMessage());
		assertEquals("testNullPointerException",
				new ApplicationException(new NullPointerException("testNullPointerException")).getCause().getMessage());
		assertEquals("message: test",
				new ApplicationException(new NullPointerException("testNullPointerException"), "message: {}", "test")
						.getMessage());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.String, java.lang.Throwable, boolean, boolean)}.
	 */
	@Test
	void testApplicationExceptionStringThrowableBooleanBoolean() {
		// to be implemented
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#checkAndThrow(java.lang.Throwable)}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#checkAndThrow(java.lang.Throwable, java.lang.String, java.lang.Object[])}
	 * and
	 * {@link accelerate.commons.exception.ApplicationException#ApplicationException(java.lang.String, java.lang.Object[])}
	 */
	@Test
	void testCheckAndThrowThrowable() {
		ApplicationException testException = new ApplicationException("testCheckAndThrowThrowable");
		try {
			ApplicationException.checkAndThrow(testException);
		} catch (ApplicationException error) {
			assertEquals(testException.toString(), error.toString());
		}

		assertThrows(ApplicationException.class, () -> ApplicationException.checkAndThrow(new NullPointerException()));

		testException = new ApplicationException("testCheckAndThrowThrowable");
		try {
			ApplicationException.checkAndThrow(new NullPointerException(), "message: {}", "test");
		} catch (ApplicationException error) {
			assertEquals("message: test", error.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#getDataMap()} and
	 * {@link accelerate.commons.exception.ApplicationException#putAnd(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testPutAnd() {
		assertEquals("value", new ApplicationException().putAnd("key", "value").getDataMap().get("key"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#putAllAnd(Object...)}
	 * and {@link accelerate.commons.exception.ApplicationException#get(String)}.
	 */
	@Test
	void testPutAllAnd() {
		assertEquals("value2", new ApplicationException().putAllAnd("key1", "value1", "key2", "value2").get("key2"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.exception.ApplicationException#toJSON()}.
	 */
	@Test
	void testToJSON() {
		assertEquals("value",
				JsonPath.parse(new ApplicationException().putAnd("key", "value").toJSON()).read("$.data.key"));
		assertEquals("testToJSON", JsonPath.parse(new ApplicationException("testToJSON").toJSON()).read("$.message"));
	}
}