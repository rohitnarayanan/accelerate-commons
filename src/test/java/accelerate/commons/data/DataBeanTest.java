package accelerate.commons.data;

import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_VALUE;
import static accelerate.commons.constant.CommonTestConstants.BEAN_NAME_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_NAME_VALUE;
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

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import accelerate.commons.util.XMLUtils;

/**
 * {@link Test} class for {@link DataBean}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since August 17, 2019
 */
@SuppressWarnings("static-method")
class DataBeanTest {
	/**
	 * {@link DataMap} for this test class
	 */
	private static final DataBean testDataBean = new TestDataBean().add(KEY, VALUE);

	/**
	 * Test method for {@link DataBean#newBean(Object[])}.
	 */
	@Test
	void testNewBean() {
		assertEquals(VALUE, DataBean.newBean(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for {@link DataBean#getDataMap()}.
	 */
	@Test
	void testGetDataMap() {
		assertEquals(1, testDataBean.getDataMap().size());
	}

	/**
	 * Test method for
	 * <ul>
	 * <li>{@link DataBean#addIgnoredFields(String[])}</li>
	 * <li>{@link DataBean#removeIgnoredFields(String[])}</li>
	 * <li>{@link DataBean#clearIgnoredFields()}</li>
	 * </ul>
	 */
	@Test
	void testIgnoredFields() {
		TestDataBean localTestDataBean = new TestDataBean();

		localTestDataBean.addIgnoredFields((String[]) null);
		localTestDataBean.addIgnoredFields(BEAN_ID_FIELD, BEAN_NAME_FIELD);
		assertThrows(PathNotFoundException.class,
				() -> JsonPath.parse(localTestDataBean.toJSON()).read("$." + BEAN_NAME_FIELD));

		localTestDataBean.removeIgnoredFields((String[]) null);
		localTestDataBean.removeIgnoredFields(BEAN_NAME_FIELD);
		assertEquals(BEAN_NAME_VALUE, JsonPath.parse(localTestDataBean.toJSON()).read("$." + BEAN_NAME_FIELD));

		localTestDataBean.clearIgnoredFields();
		assertEquals(BEAN_ID_VALUE, JsonPath.parse(localTestDataBean.toJSON()).read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link DataBean#addIgnoredFields(String[])}.
	 */
	@Test
	void testAddIgnoredFields() {
		assertTrue(true, "Tested as part of testIgnoredFields()");
	}

	/**
	 * Test method for {@link DataBean#removeIgnoredFields(String[])}.
	 */
	@Test
	void testRemoveIgnoredFields() {
		assertTrue(true, "Tested as part of testIgnoredFields()");
	}

	/**
	 * Test method for {@link DataBean#clearIgnoredFields()}.
	 */
	@Test
	void testClearIgnoredFields() {
		assertTrue(true, "Tested as part of testIgnoredFields()");
	}

	/**
	 * Test method for {@link DataBean#toJSON()}.
	 */
	@Test
	void testToJSON() {
		assertEquals(BEAN_ID_VALUE, JsonPath.parse(testDataBean.toJSON()).read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link DataBean#toXML()}.
	 */
	@Test
	void testToXML() {
		assertEquals(BEAN_ID_VALUE, XMLUtils.xPathNodeAttribute("/TestDataBean", BEAN_ID_FIELD,
				XMLUtils.stringToXML(testDataBean.toXML())));

	}

	/**
	 * Test method for {@link DataBean#toYAML()}.
	 */
	@Test
	void testToYAML() {
		assertThat(testDataBean.toYAML()).contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for {@link DataBean#add(String, Object)}.
	 */
	@Test
	void testAdd() {
		assertEquals(VALUE, new DataBean().add(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for {@link DataBean#addAll(java.util.Map)}.
	 */
	@Test
	void testAddAllMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("A", "A");
		map.put("B", "B");

		assertEquals(2, new DataBean().addAll(map).getDataMap().size());
	}

	/**
	 * Test method for {@link DataBean#addAll(Object[])}.
	 */
	@Test
	void testAddAllObjectArray() {
		assertEquals(2, new DataBean().addAll("A", "A", "B", "B").getDataMap().size());
	}

	/**
	 * Test method for {@link DataBean#get(String)}.
	 */
	@Test
	void testGet() {
		assertEquals(VALUE, testDataBean.get(KEY));
	}

	/**
	 * Test method for {@link DataBean#getOrDefault(String, Object)}.
	 */
	@Test
	void testGetOrDefault() {
		assertEquals("DEFAULT", testDataBean.getOrDefault("INVALID", "DEFAULT"));
	}

	/**
	 * Test method for {@link DataBean#getString(String)}.
	 */
	@Test
	void testGetString() {
		Object valueObj = new Object();
		assertEquals(valueObj.toString(), new DataBean().add(KEY, valueObj).getString(KEY));
	}

	/**
	 * Test method for {@link DataBean#getNumber(String, Class)}.
	 */
	@Test
	void testGetNumber() {
		assertNull(testDataBean.getNumber("INVALID", Number.class));

		assertEquals((Integer) 0, new DataBean().add(KEY, 0).getNumber(KEY, Integer.class));
		assertEquals((Long) 0L, new DataBean().add(KEY, Long.valueOf(0)).getNumber(KEY, Long.class));
		assertEquals((Double) 0D, new DataBean().add(KEY, Double.valueOf(0)).getNumber(KEY, Double.class));
	}

	/**
	 * Test method for {@link DataBean#checkValue(String, Object)}.
	 */
	@Test
	void testCheckValue() {
		assertEquals(true, testDataBean.checkValue(KEY, VALUE));
	}

	/**
	 * Test method for {@link DataBean#remove(String)}.
	 */
	@Test
	void testRemove() {
		DataBean testBean = new DataBean().add(KEY, VALUE);
		assertEquals(VALUE, testBean.remove(KEY));
		assertEquals(0, testBean.getDataMap().size());
	}
}