package accelerate.commons.exception;

import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

import accelerate.commons.util.CommonUtils;

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
		ApplicationException testException = new ApplicationException("testCheckAndThrowThrowable",
				new NullPointerException(), false, false);
		assertTrue(CommonUtils.isEmpty(testException.getStackTrace()));
		assertTrue(CommonUtils.isEmpty(testException.getSuppressed()));
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
		assertEquals(VALUE, new ApplicationException().putAnd(KEY, VALUE).getDataMap().get(KEY));
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
		assertEquals(VALUE, JsonPath.parse(new ApplicationException().putAnd(KEY, VALUE).toJSON()).read("$.data.key"));
		assertEquals("testToJSON", JsonPath.parse(new ApplicationException("testToJSON").toJSON()).read("$.message"));
	}
}