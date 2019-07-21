package accelerate.commons.data;

import static accelerate.commons.AccelerateCommonsTest.testDataBean;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_VALUE;
import static accelerate.commons.constant.CommonTestConstants.BEAN_NAME_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_NAME_VALUE;
import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import accelerate.commons.util.JacksonUtils;
import accelerate.commons.util.XMLUtils;

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
	 * Test method for {@link accelerate.commons.data.DataBean#DataBean()} and
	 * {@link accelerate.commons.data.DataBean#putAnd(java.lang.String, java.lang.Object)}.
	 */
	@Test
	void testDataBean() {
		assertEquals(VALUE, new DataBean().putAnd(KEY, VALUE).get(KEY));
	}

	/**
	 * Test method for
	 * <ul>
	 * <li>{@link accelerate.commons.data.DataBean#addIgnoredFields(java.lang.String[])}</li>
	 * <li>{@link accelerate.commons.data.DataBean#removeIgnoredFields(java.lang.String[])}</li>
	 * <li>{@link accelerate.commons.data.DataBean#clearIgnoredFields()}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toJSON()}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toString()}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toXML()}</li>
	 * <li>{@link accelerate.commons.data.DataBean#toYAML()}</li>
	 * </ul>
	 */
	@Test
	void testAddJsonIgnoreFields() {
		TestDataBean localTestDataBean = new TestDataBean();

		localTestDataBean.addIgnoredFields(new String[] {});
		localTestDataBean.addIgnoredFields(BEAN_ID_FIELD, BEAN_NAME_FIELD);
		assertThrows(PathNotFoundException.class, () -> JsonPath.parse(localTestDataBean.toJSON()).read("$.beanValue"));
		assertNotNull(localTestDataBean.toXML());
		assertNotNull(localTestDataBean.toYAML());

		localTestDataBean.removeIgnoredFields(new String[] {});
		localTestDataBean.removeIgnoredFields(BEAN_NAME_FIELD);
		assertEquals(BEAN_NAME_VALUE, JsonPath.parse(localTestDataBean.toJSON()).read("$." + BEAN_NAME_FIELD));

		localTestDataBean.clearIgnoredFields();
		assertEquals(BEAN_ID_VALUE, JsonPath.parse(localTestDataBean.toString()).read("$." + BEAN_ID_FIELD));
		assertEquals(BEAN_ID_VALUE, XMLUtils.xPathNodeAttribute("/TestDataBean", BEAN_ID_FIELD,
				XMLUtils.stringToXML(localTestDataBean.toXML())));
		assertThat(localTestDataBean.toYAML()).contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.data.DataBean#putAllAnd(java.util.Map)} and
	 * {@link accelerate.commons.data.DataBean#putAllAnd(Object...)}.
	 */
	@Test
	void testPutAllAnd() {
		Map<String, Object> map = new HashMap<>();
		map.put(KEY, VALUE);

		assertEquals(VALUE, DataBean.newBean().putAllAnd(map).get(KEY));

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
		// getDataMap
		assertEquals(VALUE, testDataBean.getDataMap().get(KEY));

		// JSONAnyGetter
		assertEquals(VALUE, JsonPath.parse(testDataBean.toString()).read("$.key"));

		// JSONAnySetter
		assertEquals(VALUE, JacksonUtils.fromJSON(JacksonUtils.buildJSON(KEY, VALUE), DataBean.class).get(KEY));
	}
}