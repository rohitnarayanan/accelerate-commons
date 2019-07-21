package accelerate.commons.util;

import static accelerate.commons.constant.CommonConstants.COMMA;
import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import accelerate.commons.constant.CommonConstants;

/**
 * {@link Test} class for {@link StringUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class StringUtilsTests {
	/**
	 * Test method for {@link StringUtils#trim(CharSequence)}.
	 */
	@Test
	void testTrim() {
		assertEquals("testTrim", StringUtils.trim(" testTrim "));
		assertEquals("test Trim", StringUtils.trim(" test Trim "));
		assertEquals("", StringUtils.trim(null));
	}

	/**
	 * Test method for {@link StringUtils#isEmpty(CharSequence)}.
	 */
	@Test
	void testIsEmpty() {
		assertTrue(StringUtils.isEmpty(" "));
		assertTrue(StringUtils.isEmpty(null));
		assertFalse(StringUtils.isEmpty("testIsEmpty"));
	}

	/**
	 * Test method for {@link StringUtils#length(CharSequence)}.
	 */
	@Test
	void testLength() {
		assertEquals(10, StringUtils.length("testLength"));
		assertEquals(10, StringUtils.length(" testLength "));
		assertEquals(0, StringUtils.length(null));
	}

	/**
	 * Test method for {@link StringUtils#toUpperCase(CharSequence)}.
	 */
	@Test
	void testToUpperCase() {
		assertEquals("TESTTOUPPERCASE", StringUtils.toUpperCase(" testToUpperCase "));
		assertEquals("TESTTOUPPERCASE", StringUtils.toUpperCase("testToUpperCase"));
	}

	/**
	 * Test method for {@link StringUtils#toLowerCase(CharSequence)}.
	 */
	@Test
	void testToLowerCase() {
		assertEquals("testtolowercase", StringUtils.toLowerCase(" testToLowerCase "));
		assertEquals("testtolowercase", StringUtils.toLowerCase("testToLowerCase"));
	}

	/**
	 * Test method for {@link StringUtils#split(CharSequence, String)}.
	 */
	@Test
	void testSplit() {
		assertEquals("c", StringUtils.split(" a,b,c ", COMMA)[2]);
	}

	/**
	 * Test method for
	 * {@link StringUtils#defaultString(CharSequence, CharSequence)}.
	 */
	@Test
	void testDefaultString() {
		assertEquals("testString", StringUtils.defaultString("testString", "testDefaultString"));
		assertEquals("testDefaultString", StringUtils.defaultString(null, "testDefaultString"));
	}

	/**
	 * Test method for {@link StringUtils#capitalize(CharSequence)}.
	 */
	@Test
	void testCapitalize() {
		assertEquals(EMPTY_STRING, StringUtils.capitalize(null));
		assertEquals("T", StringUtils.capitalize("t  "));
		assertEquals("TestCapitalize", StringUtils.capitalize("testCapitalize"));
	}
}