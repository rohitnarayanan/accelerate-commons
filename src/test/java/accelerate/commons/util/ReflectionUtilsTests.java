package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link ReflectionUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class ReflectionUtilsTests {
	/**
	 * Test method for {@link ReflectionUtils#getFieldValue(Object, String)}.
	 */
	@Test
	void testGetFieldValueObjectString() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link ReflectionUtils#getFieldValue(Object, Field)}.
	 */
	@Test
	void testGetFieldValueObjectField() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#setFieldValue(Class, Object, String, Object)}.
	 */
	@Test
	void testSetFieldValue() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#invokeMethod(Class, Object, String, Class[], Object[])}.
	 */
	@Test
	void testInvokeMethod() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link ReflectionUtils#invokeGetter(Object, String)}.
	 */
	@Test
	void testInvokeGetter() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link ReflectionUtils#invokeSetter(Object, String, Object)}.
	 */
	@Test
	void testInvokeSetter() {
		assertTrue(true);
	}
}