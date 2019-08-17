package accelerate.commons.util;

import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_VALUE;
import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import accelerate.commons.data.DataBean;
import accelerate.commons.data.DataMap;
import accelerate.commons.data.TestDataBean;

/**
 * {@link Test} class for {@link ReflectionUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since August 16, 2019
 */
@SuppressWarnings("static-method")
class ReflectionUtilsTest {
	/**
	 * {@link DataMap} for this test class
	 */
	private static final DataBean testObject = new TestDataBean().add(KEY, VALUE);

	/**
	 * static field for {@link #testSetStaticFieldValue()}
	 */
	private static String STATIC_TEST_FIELD = null;

	/**
	 * @param aParam
	 * @return
	 */
	private static String STATIC_TEST_METHOD(String aParam) {
		return aParam;
	}

	/**
	 * Test method for {@link ReflectionUtils#getFieldValue(Object, String)}.
	 */
	@Test
	void testGetFieldValue() {
		assertEquals(BEAN_ID_VALUE, ReflectionUtils.getFieldValue(testObject, BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link ReflectionUtils#getStaticFieldValue(Class, String)}.
	 */
	@Test
	void testGetStaticFieldValue() {
		assertEquals(1L, ReflectionUtils.getStaticFieldValue(TestDataBean.class, "serialVersionUID"));
	}

	/**
	 * Test method for {@link ReflectionUtils#getField(Object, String)}.
	 */
	@Test
	void testGetFieldObjectString() {
		assertEquals("ignoredFields", ReflectionUtils.getField(testObject, "ignoredFields").getName());
	}

	/**
	 * Test method for {@link ReflectionUtils#getField(Class, String)}.
	 */
	@Test
	void testGetFieldClassOfQString() {
		assertEquals("ignoredFields", ReflectionUtils.getField(TestDataBean.class, "ignoredFields").getName());
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#setFieldValue(Object, String, Object)}.
	 */
	@Test
	void testSetFieldValue() {
		TestDataBean testBean = new TestDataBean("bean1", "value1");
		ReflectionUtils.setFieldValue(testBean, BEAN_ID_FIELD, "NEW_ID");
		assertEquals("NEW_ID", testBean.getBeanId());
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#setStaticFieldValue(Class, String, Object)}.
	 */
	@Test
	void testSetStaticFieldValue() {
		ReflectionUtils.setStaticFieldValue(ReflectionUtilsTest.class, "STATIC_TEST_FIELD", "NEW_VALUE");
		assertEquals("NEW_VALUE", STATIC_TEST_FIELD);
	}

	/**
	 * Test method for {@link ReflectionUtils#invokeGetter(Object, String)}.
	 */
	@Test
	void testInvokeGetter() {
		assertEquals(BEAN_ID_VALUE, ReflectionUtils.invokeGetter(testObject, BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link ReflectionUtils#invokeSetter(Object, String, Object)}.
	 */
	@Test
	void testInvokeSetter() {
		TestDataBean testBean = new TestDataBean("bean1", "value1");
		ReflectionUtils.invokeSetter(testBean, BEAN_ID_FIELD, "NEW_VALUE");
		assertEquals("NEW_VALUE", testBean.getBeanId());
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#invokeMethod(Object, String, Class[], Object[])}.
	 */
	@Test
	void testInvokeMethod() {
		Object value = ReflectionUtils.invokeMethod(testObject, "toJSON", new Class<?>[] {}, new Object[] {});
		assertEquals(testObject.toJSON(), value);
	}

	/**
	 * Test method for
	 * {@link ReflectionUtils#invokeStaticMethod(Class, String, Class[], Object[])}.
	 */
	@Test
	void testInvokeStaticMethod() {
		assertEquals(STATIC_TEST_METHOD("NEW_VALUE"), ReflectionUtils.invokeStaticMethod(ReflectionUtilsTest.class,
				"STATIC_TEST_METHOD", new Class<?>[] { String.class }, new Object[] { "NEW_VALUE" }));
	}

	/**
	 * Test method for {@link ReflectionUtils#findMethod(Object, String, Class[])}.
	 */
	@Test
	void testFindMethodObjectStringClassOfQArray() {
		assertEquals("toJSON", ReflectionUtils.findMethod(testObject, "toJSON", new Class<?>[] {}).getName());
	}

	/**
	 * Test method for {@link ReflectionUtils#findMethod(Class, String, Class[])}.
	 */
	@Test
	void testFindMethodClassOfQStringClassOfQArray() {
		assertEquals("STATIC_TEST_METHOD",
				ReflectionUtils
						.findMethod(ReflectionUtilsTest.class, "STATIC_TEST_METHOD", new Class<?>[] { String.class })
						.getName());
	}
}