package accelerate.commons.util;

import static accelerate.commons.AccelerateCommonsTest.testDataBean;
import static accelerate.commons.AccelerateCommonsTest.testDataMap;
import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_FIELD;
import static accelerate.commons.constant.CommonTestConstants.BEAN_ID_VALUE;
import static accelerate.commons.constant.CommonTestConstants.BEAN_NAME_FIELD;
import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import accelerate.commons.data.TestDataBean;
import accelerate.commons.exception.ApplicationException;

/**
 * {@link Test} class for {@link JacksonUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 20, 2019
 */
@SuppressWarnings("static-method")
class JacksonUtilsTest {
	/**
	 * Test method for {@link accelerate.commons.util.JacksonUtils#objectMapper()}.
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testObjectMapper() throws JsonProcessingException {
		assertThat(JacksonUtils.objectMapper().writeValueAsString(testDataMap))
				.isEqualTo(JacksonUtils.buildJSON(KEY, VALUE));
	}

	/**
	 * Test method for {@link accelerate.commons.util.JacksonUtils#xmlMapper()}.
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testXmlMapper() throws JsonProcessingException {
		assertThat(JacksonUtils.xmlMapper().writeValueAsString(testDataMap))
				.isEqualTo(JacksonUtils.buildXML(KEY, VALUE));
	}

	/**
	 * Test method for {@link accelerate.commons.util.JacksonUtils#yamlMapper()}.
	 * 
	 * @throws ApplicationException
	 * @throws JsonProcessingException
	 */
	@Test
	void testYamlMapper() throws JsonProcessingException, ApplicationException {
		assertThat(JacksonUtils.yamlMapper().writeValueAsString(testDataMap))
				.isEqualTo(JacksonUtils.buildYAML(KEY, VALUE));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#configureMapper(com.fasterxml.jackson.databind.ObjectMapper)}.
	 */
	@Test
	void testConfigureMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
		JacksonUtils.configureMapper(mapper);
		assertFalse(mapper.getSerializationConfig().isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toJSON(java.lang.Object)}.
	 */
	@Test
	void testToJSON() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSON(null));
		assertEquals(BEAN_ID_VALUE, JsonPath.parse(JacksonUtils.toJSON(testDataBean)).read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toXML(java.lang.Object)}.
	 */
	@Test
	void testToXML() {
		assertEquals(BEAN_ID_VALUE, XMLUtils.xPathNodeAttribute("/TestDataBean", BEAN_ID_FIELD,
				XMLUtils.stringToXML(JacksonUtils.toXML(testDataBean))));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toYAML(java.lang.Object)}.
	 */
	@Test
	void testToYAML() {
		assertThat(JacksonUtils.toYAML(testDataBean)).contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toJSONExcludeFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToJSONExcludeFields() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSONExcludeFields(null, BEAN_NAME_FIELD));
		assertThrows(PathNotFoundException.class, () -> JsonPath
				.parse(JacksonUtils.toJSONExcludeFields(testDataBean, BEAN_NAME_FIELD)).read("$." + BEAN_NAME_FIELD));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toXMLExcludeFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToXMLExcludeFields() {
		assertTrue(StringUtils.isEmpty(XMLUtils.xPathNodeValue("/TestDataBean/" + BEAN_NAME_FIELD,
				XMLUtils.stringToXML(JacksonUtils.toXMLExcludeFields(testDataBean, BEAN_NAME_FIELD)))));

	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toYAMLExcludeFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToYAMLExcludeFields() {
		assertThat(JacksonUtils.toYAMLExcludeFields(testDataBean, BEAN_NAME_FIELD)).doesNotContain(BEAN_NAME_FIELD);
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toJSONSelectFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToJSONSelectFields() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSONSelectFields(null, BEAN_NAME_FIELD));

		DocumentContext context = JsonPath.parse(JacksonUtils.toJSONSelectFields(testDataBean, BEAN_ID_FIELD));
		assertThrows(PathNotFoundException.class, () -> context.read("$." + BEAN_NAME_FIELD));
		assertEquals(BEAN_ID_VALUE, context.read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toXMLSelectFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToXMLSelectFields() {
		assertTrue(true, "https://github.com/FasterXML/jackson-dataformat-xml/issues/351");
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#toYAMLSelectFields(java.lang.Object, java.lang.String[])}.
	 */
	@Test
	void testToYAMLSelectFields() {
		assertThat(JacksonUtils.toYAMLSelectFields(testDataBean, BEAN_ID_FIELD)).doesNotContain(BEAN_NAME_FIELD)
				.contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#fromJSON(java.lang.String, java.lang.Class)}.
	 */
	@Test
	void testFromJSON() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromJSON(testDataBean.toJSON(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#fromXML(java.lang.String, java.lang.Class)}.
	 */
	@Test
	void testFromXML() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromXML(testDataBean.toXML(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#fromYAML(java.lang.String, java.lang.Class)}.
	 */
	@Test
	void testFromYAML() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromYAML(testDataBean.toYAML(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#buildJSON(Object...)}.
	 */
	@Test
	void testBuildJSONObjectArray() {
		assertEquals(VALUE, JsonPath.parse(JacksonUtils.buildJSON(KEY, VALUE)).read("$.key"));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#buildXML(java.lang.Object[])}.
	 */
	@Test
	void testBuildXMLObjectArray() {
		assertEquals(VALUE,
				XMLUtils.xPathNodeValue("/Data/key", XMLUtils.stringToXML(JacksonUtils.buildXML(KEY, VALUE))));
	}

	/**
	 * Test method for
	 * {@link accelerate.commons.util.JacksonUtils#buildYAML(java.lang.Object[])}.
	 */
	@Test
	void testBuildYAMLObjectArray() {
		assertThat(JacksonUtils.buildYAML(KEY, VALUE)).contains("key: \"value\"");
	}
}
