package accelerate.commons.util;

import static accelerate.commons.AccelerateCommonsTest.testDataBean;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#getFieldValue(Object, String)}.
	 */
	@Test
	void testGetFieldValue() {
		assertEquals(BEAN_ID_VALUE, ReflectionUtils.getFieldValue(testDataBean, BEAN_ID_FIELD));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#getStaticFieldValue(Class, String)}.
	 */
	@Test
	void testGetStaticFieldValue() {
		assertEquals(1L, ReflectionUtils.getStaticFieldValue(TestDataBean.class, "serialVersionUID"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#getField(Object, String)}.
	 */
	@Test
	void testGetFieldObjectString() {
		assertEquals("ignoredFields", ReflectionUtils.getField(testDataBean, "ignoredFields").getName());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#getField(Class, String)}.
	 */
	@Test
	void testGetFieldClassOfQString() {
		assertEquals("ignoredFields", ReflectionUtils.getField(TestDataBean.class, "ignoredFields").getName());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#setFieldValue(Object, String, Object)}.
	 */
	@Test
	void testSetFieldValue() {
		TestDataBean testBean = new TestDataBean("bean1", "value1");
		ReflectionUtils.setFieldValue(testBean, BEAN_ID_FIELD, "NEW_ID");
		assertEquals("NEW_ID", testBean.getBeanId());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#setStaticFieldValue(Class, String, Object)}.
	 */
	@Test
	void testSetStaticFieldValue() {
		ReflectionUtils.setStaticFieldValue(ReflectionUtilsTest.class, "STATIC_TEST_FIELD", "NEW_VALUE");
		assertEquals("NEW_VALUE", STATIC_TEST_FIELD);
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#invokeGetter(Object, String)}.
	 */
	@Test
	void testInvokeGetter() {
		assertEquals(BEAN_ID_VALUE, ReflectionUtils.invokeGetter(testDataBean, BEAN_ID_FIELD));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#invokeSetter(Object, String, Object)}.
	 */
	@Test
	void testInvokeSetter() {
		TestDataBean testBean = new TestDataBean("bean1", "value1");
		ReflectionUtils.invokeSetter(testBean, BEAN_ID_FIELD, "NEW_VALUE");
		assertEquals("NEW_VALUE", testBean.getBeanId());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#invokeMethod(Object, String, Class[], Object[])}.
	 */
	@Test
	void testInvokeMethod() {
		Object value = ReflectionUtils.invokeMethod(testDataBean, "toJSON", new Class<?>[] {}, new Object[] {});
		assertEquals(testDataBean.toJSON(), value);
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#invokeStaticMethod(Class, String, Class[], Object[])}.
	 */
	@Test
	void testInvokeStaticMethod() {
		assertEquals(STATIC_TEST_METHOD("NEW_VALUE"), ReflectionUtils.invokeStaticMethod(ReflectionUtilsTest.class,
				"STATIC_TEST_METHOD", new Class<?>[] { String.class }, new Object[] { "NEW_VALUE" }));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#findMethod(Object, String, Class[])}.
	 */
	@Test
	void testFindMethodObjectStringClassOfQArray() {
		assertEquals("toJSON", ReflectionUtils.findMethod(testDataBean, "toJSON", new Class<?>[] {}).getName());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.ReflectionUtils#findMethod(Class, String, Class[])}.
	 */
	@Test
	void testFindMethodClassOfQStringClassOfQArray() {
		assertEquals("STATIC_TEST_METHOD",
				ReflectionUtils
						.findMethod(ReflectionUtilsTest.class, "STATIC_TEST_METHOD", new Class<?>[] { String.class })
						.getName());
	}
}