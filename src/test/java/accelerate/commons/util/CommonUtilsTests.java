package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import accelerate.commons.data.DataMap;
import accelerate.commons.exception.ApplicationException;

/**
 * {@link Test} class for {@link CommonUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class CommonUtilsTests {
	/**
	 * Test method for {@link CommonUtils#isEmptyAny(java.lang.Object[])}.
	 */
	@Test
	void testIsEmptyAny() {
		assertTrue(CommonUtils.isEmptyAny("a", null, "b"));
		assertFalse(CommonUtils.isEmptyAny("a", "b"));
	}

	/**
	 * Test method for {@link CommonUtils#isEmptyAll(java.lang.Object[])}.
	 */
	@Test
	void testIsEmptyAll() {
		assertTrue(CommonUtils.isEmptyAll(null, ""));
		assertFalse(CommonUtils.isEmptyAll("a", ""));
	}

	/**
	 * Test method for {@link CommonUtils#xor(boolean, boolean)}.
	 */
	@Test
	void testXor() {
		assertTrue(CommonUtils.xor(true, false));
		assertTrue(CommonUtils.xor(false, true));
		assertFalse(CommonUtils.xor(true, true));
		assertFalse(CommonUtils.xor(false, false));
	}

	/**
	 * Test method for
	 * {@link CommonUtils#compare(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	void testCompare() {
		// test null input
		assertFalse(CommonUtils.compare(null, "b"));
		assertFalse(CommonUtils.compare("a", null));

		// test comparable input
		assertTrue(CommonUtils.compare("a", "a"));
		assertFalse(CommonUtils.compare("a", "b"));

		// test other input
		DataMap map1 = DataMap.newMap("a", "b");
		DataMap map2 = DataMap.newMap("a", "b");
		assertTrue(CommonUtils.compare(map1, map2));
	}

	/**
	 * Test method for {@link CommonUtils#compareAny(Object, Object...)}.
	 */
	@Test
	void testCompareAnyArray() {
		/*
		 * No test required as all logic is in {@link CommonUtils#compareAny(Object,
		 * List)}. This method simply convert the array to list and delegates. The
		 * conversion is already tested as part of {@link
		 * CollectionUtils#toList(Object...)}
		 */
		assertTrue(true);
	}

	/**
	 * Test method for {@link CommonUtils#compareAny(Object, List)}.
	 */
	@Test
	void testCompareAnyList() {
		List<String> testList = CollectionUtils.toList("a", "b", "c", "d");

		// test empty inputs
		assertFalse(CommonUtils.compareAny(null, testList));
		assertFalse(CommonUtils.compareAny("", testList));
		assertFalse(CommonUtils.compareAny("a", (List<String>) null));

		// test valid inputs
		assertTrue(CommonUtils.compareAny("a", testList));
		assertFalse(CommonUtils.compareAny("e", testList));
	}

	/**
	 * Test method for {@link CommonUtils#getErrorMessage(Throwable)}.
	 */
	@Test
	void testGetErrorMessage() {
		// test null input
		assertTrue(CommonUtils.getErrorMessage(null).isEmpty());

		// test valid inputs
		assertEquals("accelerate.commons.exception.ApplicationException",
				CommonUtils.getErrorMessage(new ApplicationException()));
		assertEquals("exception message", CommonUtils.getErrorMessage(new ApplicationException("exception message")));
	}

	/**
	 * Test method for {@link CommonUtils#getErrorLog(Throwable)}.
	 */
	@Test
	void testGetErrorLog() {
		// test null input
		assertTrue(CommonUtils.getErrorLog(null).isEmpty());

		// No need to test the string conversion of Exception.printStackTrace()
	}
}