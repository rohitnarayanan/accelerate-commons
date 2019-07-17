package accelerate.commons.data;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static accelerate.commons.constant.CommonConstants.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
	 * {@link accelerate.commons.data.DataBean#newBean(java.lang.Object[])} and
	 * {@link accelerate.commons.data.DataBean#getDataMap()}
	 */
	@Test
	void testNewBean() {
		assertEquals(1, DataBean.newBean(KEY, VALUE).getDataMap().size());
	}

	/**
	 * Test method for {@link accelerate.commons.data.DataBean#DataBean()}.
	 */
	@Test
	void testDataBean() {
		assertEquals(1, new DataBean().putAnd(KEY, VALUE).getDataMap().size());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#DataBean(java.lang.String)} and
	 * {@link accelerate.commons.data.DataBean#toShortJSON()}
	 */
	@Test
	void testDataBeanString() {
		Assertions.assertThat(DataBean.newBean(KEY, VALUE).toShortJSON().replaceAll(SPACE, EMPTY_STRING))
				.startsWith("{\"id\":");

		TestDataBean testDataBean = new TestDataBean();
		testDataBean.setBeanId("1234");
		assertEquals(JSONUtils.buildJSON("beanId", "1234"), testDataBean.toShortJSON().replaceAll(SPACE, EMPTY_STRING));
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
		assertEquals(JSONUtils.buildJSON("beanId", "1234"), testDataBean.toJSON().replaceAll(SPACE, EMPTY_STRING));

		testDataBean.addJsonIgnoreFields("beanId");
		testDataBean.removeJsonIgnoreFields("beanValue");
		assertEquals(JSONUtils.buildJSON("beanValue", "Value1234"),
				testDataBean.toString().replaceAll(SPACE, EMPTY_STRING));
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
