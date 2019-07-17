package accelerate.commons.data;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static accelerate.commons.constant.CommonConstants.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link DataMap}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 16, 2019
 */
@SuppressWarnings("static-method")
class DataMapTest {
	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#newMap(java.lang.Object[])}.
	 */
	@Test
	void testNewMap() {
		assertEquals(1, DataMap.newMap(KEY, VALUE).size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#putAnd(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testPutAnd() {
		assertEquals(1, DataMap.newMap().putAnd(KEY, VALUE).size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#putAllAnd(java.util.Map)}.
	 */
	@Test
	void testPutAllAndMapOfQextendsStringQextendsObject() {
		Map<String, Object> map = new HashMap<>();
		map.put(KEY, VALUE);
		assertEquals(1, DataMap.newMap().putAllAnd(map).size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#putAllAnd(java.lang.Object[])}.
	 */
	@Test
	void testPutAllAndObjectArray() {
		assertEquals(1, DataMap.newMap().putAllAnd(KEY, VALUE).size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#get(java.lang.String)}.
	 */
	@Test
	void testGet() {
		assertEquals(VALUE, DataMap.newMap(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#getString(java.lang.String)}.
	 */
	@Test
	void testGetString() {
		Object valueObj = new Object();
		assertEquals(valueObj.toString(), DataMap.newMap(KEY, valueObj).getString(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#getInt(java.lang.String)}.
	 */
	@Test
	void testGetInt() {
		assertEquals((Integer) 0, DataMap.newMap(KEY, 0).getInt(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#checkValue(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testCheckValue() {
		assertEquals(true, DataMap.newMap(KEY, VALUE).checkValue(KEY, VALUE));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#remove(java.lang.String)}.
	 */
	@Test
	void testRemoveString() {
		assertEquals(VALUE, DataMap.newMap(KEY, VALUE).remove(KEY));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#toJSON()} and
	 * {@link accelerate.commons.data.DataMap#toString()}
	 */
	@Test
	void testToJSONAndToString() {
		DataMap dataMap = DataMap.newMap(KEY, VALUE);
		String expected = "{\"key\":\"value\"}";

		assertEquals(expected, dataMap.toJSON().replaceAll(SPACE, EMPTY_STRING));
		assertEquals(expected, dataMap.toString().replaceAll(SPACE, EMPTY_STRING));
	}

	/**
	 * Key
	 */
	private static String KEY = "key";

	/**
	 * Value
	 */
	private static String VALUE = "value";
}