package accelerate.commons.util;

import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import accelerate.commons.data.DataBean;
import accelerate.commons.data.DataMap;

/**
 * {@link Test} class for {@link CollectionUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class CollectionUtilsTests {
	/**
	 * Test method for
	 * {@link accelerate.commons.util.CollectionUtils#convertToMap(Properties, java.lang.Class)}.
	 */
	@Test
	void testConvertToMap() {
		assertEquals(0, CollectionUtils.convertToMap(null, String.class).size());

		Properties testProps = new Properties();
		assertEquals(0, CollectionUtils.convertToMap(testProps, String.class).size());

		// Test string value
		testProps.put(KEY, VALUE);
		assertEquals(1, CollectionUtils.convertToMap(testProps, String.class).size());

		// Test other type value
		testProps.put(KEY, DataBean.newBean("innerKey", "innerValue"));
		assertEquals("innerValue",
				CollectionUtils.convertToMap(testProps, DataBean.class).get(KEY).getString("innerKey"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.CollectionUtils#compareMaps(Map, Map)}.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testCompareMaps() {
		DataMap mapA = DataMap.newMap("a", "a", "b", "b", "c", "c");
		DataMap mapB = DataMap.newMap("b", "b", "c", "c1", "d", "d");
		DataMap result = CollectionUtils.compareMaps(mapA, mapB);

		// "extraA", extraA, "extraB", extraB, "conflict", conflict
		Map<String, Object> extraA = (Map<String, Object>) result.get("extraA");
		Map<String, Object> extraB = (Map<String, Object>) result.get("extraB");
		Map<String, Object> conflict = (Map<String, Object>) result.get("conflict");

		Assertions.assertNotNull(extraA.get("a"));
		Assertions.assertNotNull(extraB.get("d"));
		Assertions.assertNotNull(conflict.get("c"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.CollectionUtils#toList(Object...)}.
	 */
	@Test
	void testToList() {
		// null input
		assertEquals(0, CollectionUtils.toList((Object[]) null).size());

		// empty input
		assertEquals(0, CollectionUtils.toList(new Object[] {}).size());

		// varargs input
		assertEquals(2, CollectionUtils.toList("a", "b").size());

		// array input
		assertEquals(1, CollectionUtils.toList(new String[] { "a" }).size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.CollectionUtils#subList(List, int, int)}.
	 */
	@Test
	void testExtractUptoList() {
		// null input
		assertEquals(0, CollectionUtils.subList((List<?>) null, 0, 0).size());

		// empty input
		assertEquals(0, CollectionUtils.subList(new ArrayList<>(), 0, 0).size());

		// test list
		List<String> testList = CollectionUtils.toList("a", "b", "c", "d");

		// basic
		assertEquals("a", CollectionUtils.subList(testList, 0, 2).get(0));
		assertEquals("c", CollectionUtils.subList(testList, 2, 4).get(0));

		// negative start
		assertEquals("a", CollectionUtils.subList(testList, -5, 2).get(0));
		assertEquals("c", CollectionUtils.subList(testList, -2, 4).get(0));

		// negative end
		assertEquals("c", CollectionUtils.subList(testList, 0, -1).get(2));
		assertEquals(0, CollectionUtils.subList(testList, 2, -2).size());
	}

	/**
	 * Test method for {@link accelerate.commons.util.CollectionUtils#sort(List)}.
	 */
	@Test
	void testSort() {
		assertFalse(CollectionUtils.sort(null));
		assertFalse(CollectionUtils.sort(new ArrayList<>()));

		List<String> testList = CollectionUtils.toList("b", "a");
		assertTrue(CollectionUtils.sort(testList));
		assertEquals("a", testList.get(0));
	}
}
