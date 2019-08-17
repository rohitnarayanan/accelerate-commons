package accelerate.commons.data;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static accelerate.commons.constant.CommonConstants.SPACE;
import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import accelerate.commons.exception.ApplicationException;
import accelerate.commons.util.CommonUtils;

/**
 * {@link Test} class for {@link DataMap}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since August 17, 2019
 */
@SuppressWarnings("static-method")
class DataMapTest {
	/**
	 * {@link DataMap} for this test class
	 */
	private static final DataMap testDataMap = DataMap.newMap(KEY, VALUE);

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#newMap(Object[])}.
	 */
	@Test
	void testNewMap() {
		assertTrue(CommonUtils.isEmpty(DataMap.newMap()));
		assertTrue(CommonUtils.isEmpty(DataMap.newMap((Object[]) null)));
		assertEquals(1, DataMap.newMap(KEY, VALUE).size());

		assertThrows(ApplicationException.class, () -> DataMap.newMap(KEY));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#add(String, Object)}.
	 */
	@Test
	void testAdd() {
		assertEquals(1, new DataMap().add(KEY, VALUE).size());
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#addAll(Map)}.
	 */
	@Test
	void testAddAllMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("A", "A");
		map.put("B", "B");

		assertEquals(2, new DataMap().addAll(map).size());
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#addAll(Object[])}.
	 */
	@Test
	void testAddAllObjectArray() {
		assertEquals(2, new DataMap().addAll("A", "A", "B", "B").size());
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#get(String)}.
	 */
	@Test
	void testGet() {
		assertEquals(VALUE, testDataMap.get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#getOrDefault(String, Object)}.
	 */
	@Test
	void testGetOrDefaultStringT() {
		assertEquals("DEFAULT", testDataMap.getOrDefault("INVALID", "DEFAULT"));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#getString(String)}.
	 */
	@Test
	void testGetString() {
		Object valueObj = new Object();
		assertEquals(valueObj.toString(), DataMap.newMap(KEY, valueObj).getString(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#getNumber(String, Class)}.
	 */
	@Test
	void testGetNumber() {
		assertNull(testDataMap.getNumber("INVALID", Number.class));

		assertEquals((Integer) 0, DataMap.newMap(KEY, 0).getNumber(KEY, Integer.class));
		assertEquals((Long) 0L, DataMap.newMap(KEY, Long.valueOf(0)).getNumber(KEY, Long.class));
		assertEquals((Double) 0D, DataMap.newMap(KEY, Double.valueOf(0)).getNumber(KEY, Double.class));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataMap#checkValue(String, Object)}.
	 */
	@Test
	void testCheckValue() {
		assertEquals(true, testDataMap.checkValue(KEY, VALUE));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#remove(String)}.
	 */
	@Test
	void testRemoveString() {
		DataMap testMap = DataMap.newMap(KEY, VALUE);
		assertEquals(VALUE, testMap.remove(KEY));
		assertEquals(0, testMap.size());
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#toJSON()}.
	 */
	@Test
	void testToJSON() {
		assertEquals("{\"key\":\"value\"}", testDataMap.toJSON().replaceAll(SPACE, EMPTY_STRING));
		assertThat(testDataMap.toJSON()).contains("\"key\":");
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#toXML()}.
	 */
	@Test
	void testToXML() {
		assertThat(testDataMap.toXML()).contains("<DataMap>");
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataMap#toYAML()}.
	 */
	@Test
	void testToYAML() {
		assertThat(testDataMap.toYAML()).contains("key:");
	}
}
