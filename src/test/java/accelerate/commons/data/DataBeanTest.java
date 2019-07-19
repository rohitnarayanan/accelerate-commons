package accelerate.commons.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

import accelerate.commons.util.JSONUtils;

/**
 * {@link Test} class for {@link DataBean}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 16, 2019
 */
@SuppressWarnings("static-method")
class DataBeanTest {
	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#newBean(java.lang.Object[])}
	 */
	@Test
	void testNewBean() {
		assertEquals(VALUE, DataBean.newBean(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataBean#DataBean()}.
	 */
	@Test
	void testDataBean() {
		assertEquals(VALUE, new DataBean().putAnd(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#DataBean(java.lang.String)} and
	 * {@link accelerate.commons.data.DataBean#toShortJSON()}
	 */
	@Test
	void testDataBeanString() {
		DataBean dataBean = DataBean.newBean(KEY, VALUE);
		String objectToString = DataBean.class.getName() + "@" + Integer.toHexString(dataBean.hashCode());
		assertEquals(objectToString, JsonPath.parse(dataBean.toShortJSON()).read("$.id"));

		TestDataBean testDataBean = new TestDataBean();
		testDataBean.setBeanId("1234");
		assertEquals("1234", JsonPath.parse(testDataBean.toShortJSON()).read("$.beanId"));
	}

	/**
	 * Test method for
	 * <ul>
	 * <li>{@link accelerate.commons.data.DataBean#addJsonIgnoreFields(java.lang.String[])}</li>
	 * <li>{@link accelerate.commons.data.DataBean#removeJsonIgnoreFields(java.lang.String[])}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toJSON()}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toJSON(boolean)}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toString()}</li>
	 * </ul>
	 */
	@Test
	void testAddJsonIgnoreFields() {
		TestDataBean testDataBean = new TestDataBean();
		testDataBean.setBeanId("1234");
		testDataBean.setBeanValue("Value1234");

		testDataBean.addJsonIgnoreFields("beanValue");
		assertEquals("1234", JsonPath.parse(testDataBean.toJSON()).read("$.beanId"));

		testDataBean.addJsonIgnoreFields("beanId");
		testDataBean.removeJsonIgnoreFields("beanValue");
		assertEquals("Value1234", JsonPath.parse(testDataBean.toString()).read("$.beanValue"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#putAnd(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testPutAnd() {
		assertEquals(VALUE, DataBean.newBean().putAnd(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#putAllAnd(java.util.Map)}.
	 */
	@Test
	void testPutAllAnd() {
		Map<String, Object> map = new HashMap<>();
		map.put(KEY, VALUE);

		assertEquals(VALUE, DataBean.newBean().putAllAnd(map).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#putAllAnd(Object...)}.
	 */
	@Test
	void testputAllAndVarargs() {
		assertEquals(VALUE, DataBean.newBean().putAllAnd(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#get(java.lang.String)}.
	 */
	@Test
	void testGet() {
		assertEquals(VALUE, DataBean.newBean(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#getString(java.lang.String)}.
	 */
	@Test
	void testGetString() {
		Object valueObj = new Object();
		assertEquals(valueObj.toString(), DataBean.newBean(KEY, valueObj).getString(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#getInt(java.lang.String)}.
	 */
	@Test
	void testGetInt() {
		assertEquals((Integer) 0, DataBean.newBean(KEY, 0).getInt(KEY));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#checkValue(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testCheckValue() {
		assertEquals(true, DataBean.newBean(KEY, VALUE).checkValue(KEY, VALUE));
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataBean#getDataMap()}
	 */
	@Test
	void testGetDataMap() {
		TestDataBean testDataBean = new TestDataBean();
		testDataBean.setBeanId("1234");
		testDataBean.setBeanValue("Value1234");
		testDataBean.putAnd(KEY, VALUE);

		// getDataMap
		assertEquals(VALUE, testDataBean.getDataMap().get(KEY));

		// JSONAnyGetter
		assertEquals(VALUE, JsonPath.parse(testDataBean.toString()).read("$.key"));

		// JSONAnySetter
		assertEquals(VALUE, JSONUtils.deserialize(JSONUtils.buildJSON(KEY, VALUE), DataBean.class).get(KEY));
	}

	@SuppressWarnings({ "javadoc", "serial" })
	class TestDataBean extends DataBean {
		private String beanId = null;
		private String beanValue = null;

		public TestDataBean() {
			super("beanId");
		}

		public String getBeanId() {
			return this.beanId;
		}

		public void setBeanId(String aBeanId) {
			this.beanId = aBeanId;
		}

		public String getBeanValue() {
			return this.beanValue;
		}

		public void setBeanValue(String aBeanValue) {
			this.beanValue = aBeanValue;
		}
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
