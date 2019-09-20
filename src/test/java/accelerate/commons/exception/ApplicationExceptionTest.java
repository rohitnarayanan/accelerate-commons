package accelerate.commons.exception;

import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

import accelerate.commons.util.CommonUtils;

/**
 * {@link Test} class for {@link ApplicationException}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 19, 2019
 */
@SuppressWarnings("static-method")
class ApplicationExceptionTest {
	/**
	 * {@link ApplicationException} instance for this test class
	 */
	private static ApplicationException testException = new ApplicationException().addAll(KEY, VALUE);

	/**
	 * Test method for {@link ApplicationException#ApplicationException()}.
	 */
	@Test
	void testApplicationException() {
		assertNotNull(testException.getStackTrace());
	}

	/**
	 * Test method for {@link ApplicationException#ApplicationException(String)}.
	 */
	@Test
	void testApplicationExceptionString() {
		assertEquals("testApplicationExceptionString",
				new ApplicationException("testApplicationExceptionString").getMessage());
	}

	/**
	 * Test method for
	 * {@link ApplicationException#ApplicationException(String, Throwable)}.
	 */
	@Test
	void testApplicationExceptionStringThrowable() {
		assertEquals("testApplicationExceptionStringThrowable",
				new ApplicationException("testApplicationExceptionStringThrowable",
						new NullPointerException("testNullPointerException")).getMessage());
	}

	/**
	 * Test method for {@link ApplicationException#ApplicationException(Throwable)}.
	 */
	@Test
	void testApplicationExceptionThrowable() {
		assertEquals("java.lang.NullPointerException: testApplicationExceptionThrowable",
				new ApplicationException(new NullPointerException("testApplicationExceptionThrowable")).getMessage());
	}

	/**
	 * Test method for
	 * {@link ApplicationException#ApplicationException(String, Throwable, boolean, boolean)}.
	 */
	@Test
	void testApplicationExceptionStringThrowableBooleanBoolean() {
		ApplicationException exception = new ApplicationException("testCheckAndThrowThrowable",
				new NullPointerException(), false, false);
		assertTrue(CommonUtils.isEmpty(exception.getStackTrace()));
		assertTrue(CommonUtils.isEmpty(exception.getSuppressed()));
	}

	/**
	 * Test method for
	 * {@link ApplicationException#ApplicationException(String, Object[])}.
	 */
	@Test
	void testApplicationExceptionStringObjectArray() {
		assertEquals("message: testApplicationExceptionStringObjectArray",
				new ApplicationException("message: {}{}", "test", "ApplicationExceptionStringObjectArray")
						.getMessage());
	}

	/**
	 * Test method for
	 * {@link ApplicationException#ApplicationException(Throwable, String, Object[])}.
	 */
	@Test
	void testApplicationExceptionThrowableStringObjectArray() {
		assertEquals("message: test",
				new ApplicationException(new NullPointerException("testNullPointerException"), "message: {}", "test")
						.getMessage());
	}

	/**
	 * Test method for {@link ApplicationException#checkAndThrow(Throwable)}.
	 */
	@Test
	void testCheckAndThrowThrowable() {
		assertThrows(ApplicationException.class, () -> ApplicationException.checkAndThrow(new NullPointerException()));

		try {
			ApplicationException.checkAndThrow(testException);
		} catch (ApplicationException error) {
			assertEquals(testException.toString(), error.toString());
		}
	}

	/**
	 * Test method for
	 * {@link ApplicationException#checkAndThrow(Throwable, String, Object[])}.
	 */
	@Test
	void testCheckAndThrowThrowableStringObjectArray() {
		try {
			ApplicationException.checkAndThrow(new NullPointerException(), "message: {}", "test");
		} catch (ApplicationException error) {
			assertEquals("message: test", error.getMessage());
		}
	}

	/**
	 * Test method for {@link ApplicationException#getDataMap()}.
	 */
	@Test
	void testGetDataMap() {
		assertEquals(VALUE, testException.getDataMap().get(KEY));
	}

	/**
	 * Test method for {@link ApplicationException#toJSON()}.
	 */
	@Test
	void testToJSON() {
		assertEquals("testToJSON", JsonPath.parse(new ApplicationException("testToJSON").toJSON()).read("$.message"));
		assertNotNull(JsonPath.parse(testException.toJSON()).read("$.stacktrace"));
		assertEquals(VALUE, JsonPath.parse(testException.toJSON()).read("$.data.key"));
	}

	/**
	 * Test method for {@link ApplicationException#toXML()}.
	 */
	@Test
	void testToXML() {
		assertThat(testException.toXML()).contains("<ApplicationException>");
	}

	/**
	 * Test method for {@link ApplicationException#toYAML()}.
	 */
	@Test
	void testToYAML() {
		assertThat(testException.toYAML()).contains("stacktrace:");
	}

	/**
	 * Test method for {@link ApplicationException#add(String, Object)}.
	 */
	@Test
	void testAdd() {
		assertEquals(VALUE, new ApplicationException().add(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for {@link ApplicationException#addAll(Map)}.
	 */
	@Test
	void testAddAllMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("A", "A");
		map.put("B", "B");

		assertEquals(2, new ApplicationException().addAll(map).getDataMap().size());
	}

	/**
	 * Test method for {@link ApplicationException#addAll(Object[])}.
	 */
	@Test
	void testAddAllObjectArray() {
		assertEquals(2, new ApplicationException().addAll("A", "A", "B", "B").getDataMap().size());
	}

	/**
	 * Test method for {@link ApplicationException#get(String)}.
	 */
	@Test
	void testGet() {
		assertEquals(VALUE, testException.get(KEY));
	}

	/**
	 * Test method for {@link ApplicationException#getOrDefault(String, Object)}.
	 */
	@Test
	void testGetOrDefault() {
		assertEquals("DEFAULT", testException.getOrDefault("INVALID", "DEFAULT"));
	}

	/**
	 * Test method for {@link ApplicationException#getString(String)}.
	 */
	@Test
	void testGetString() {
		Object valueObj = new Object();
		assertEquals(valueObj.toString(), new ApplicationException().add(KEY, valueObj).getString(KEY));
	}

	/**
	 * Test method for {@link ApplicationException#getNumber(String, Class)}.
	 */
	@Test
	void testGetNumber() {
		assertNull(testException.getNumber("INVALID", Number.class));

		assertEquals((Integer) 0, new ApplicationException().add(KEY, 0).getNumber(KEY, Integer.class));
		assertEquals((Long) 0L, new ApplicationException().add(KEY, Long.valueOf(0)).getNumber(KEY, Long.class));
		assertEquals((Double) 0D, new ApplicationException().add(KEY, Double.valueOf(0)).getNumber(KEY, Double.class));
	}

	/**
	 * Test method for {@link ApplicationException#checkValue(String, Object)}.
	 */
	@Test
	void testCheckValue() {
		assertEquals(true, testException.checkValue(KEY, VALUE));
	}

	/**
	 * Test method for {@link ApplicationException#remove(String)}.
	 */
	@Test
	void testRemove() {
		ApplicationException exception = new ApplicationException().add(KEY, VALUE);
		assertEquals(VALUE, exception.remove(KEY));
		assertEquals(0, exception.getDataMap().size());
	}
}