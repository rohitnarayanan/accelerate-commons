package accelerate.commons.util;

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

import accelerate.commons.data.DataBean;
import accelerate.commons.data.DataMap;
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
	 * {@link DataMap} for this test class
	 */
	private static final DataMap testMap = DataMap.newMap(KEY, VALUE);

	/**
	 * {@link DataMap} for this test class
	 */
	private static final DataBean testBean = new TestDataBean().add(KEY, VALUE);

	/**
	 * Test method for {@link JacksonUtils#objectMapper()}.
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testObjectMapper() throws JsonProcessingException {
		assertThat(JacksonUtils.objectMapper().writeValueAsString(testMap))
				.isEqualTo(JacksonUtils.buildJSON(KEY, VALUE));
	}

	/**
	 * Test method for {@link JacksonUtils#xmlMapper()}.
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testXmlMapper() throws JsonProcessingException {
		assertThat(JacksonUtils.xmlMapper().writeValueAsString(testMap))
				.isEqualTo(JacksonUtils.buildXML("DataMap", KEY, VALUE));
	}

	/**
	 * Test method for {@link JacksonUtils#yamlMapper()}.
	 * 
	 * @throws ApplicationException
	 * @throws JsonProcessingException
	 */
	@Test
	void testYamlMapper() throws JsonProcessingException, ApplicationException {
		assertThat(JacksonUtils.yamlMapper().writeValueAsString(testMap)).isEqualTo(JacksonUtils.buildYAML(KEY, VALUE));
	}

	/**
	 * Test method for
	 * {@link JacksonUtils#configureMapper(com.fasterxml.jackson.databind.ObjectMapper)}.
	 */
	@Test
	void testConfigureMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
		JacksonUtils.configureMapper(mapper);
		assertFalse(mapper.getSerializationConfig().isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
	}

	/**
	 * Test method for {@link JacksonUtils#toJSON(Object)}.
	 */
	@Test
	void testToJSON() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSON(null));
		assertEquals(BEAN_ID_VALUE, JsonPath.parse(JacksonUtils.toJSON(testBean)).read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link JacksonUtils#toXML(Object)}.
	 */
	@Test
	void testToXML() {
		assertEquals(BEAN_ID_VALUE, XMLUtils.xPathNodeAttribute("/TestDataBean", BEAN_ID_FIELD,
				XMLUtils.stringToXML(JacksonUtils.toXML(testBean))));
	}

	/**
	 * Test method for {@link JacksonUtils#toYAML(Object)}.
	 */
	@Test
	void testToYAML() {
		assertThat(JacksonUtils.toYAML(testBean)).contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for {@link JacksonUtils#toJSONExcludeFields(Object, String[])}.
	 */
	@Test
	void testToJSONExcludeFields() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSONExcludeFields((Object) null, BEAN_NAME_FIELD));
		assertThrows(PathNotFoundException.class, () -> JsonPath
				.parse(JacksonUtils.toJSONExcludeFields(testBean, BEAN_NAME_FIELD)).read("$." + BEAN_NAME_FIELD));
	}

	/**
	 * Test method for {@link JacksonUtils#toXMLExcludeFields(Object, String[])}.
	 */
	@Test
	void testToXMLExcludeFields() {
		assertTrue(StringUtils.isEmpty(XMLUtils.xPathNodeValue("/TestDataBean/" + BEAN_NAME_FIELD,
				XMLUtils.stringToXML(JacksonUtils.toXMLExcludeFields(testBean, BEAN_NAME_FIELD)))));

	}

	/**
	 * Test method for {@link JacksonUtils#toYAMLExcludeFields(Object, String[])}.
	 */
	@Test
	void testToYAMLExcludeFields() {
		assertThat(JacksonUtils.toYAMLExcludeFields(testBean, BEAN_NAME_FIELD)).doesNotContain(BEAN_NAME_FIELD);
	}

	/**
	 * Test method for {@link JacksonUtils#toJSONSelectFields(Object, String[])}.
	 */
	@Test
	void testToJSONSelectFields() {
		assertEquals(EMPTY_STRING, JacksonUtils.toJSONSelectFields((Object) null, BEAN_NAME_FIELD));

		DocumentContext context = JsonPath.parse(JacksonUtils.toJSONSelectFields(testBean, BEAN_ID_FIELD));
		assertThrows(PathNotFoundException.class, () -> context.read("$." + BEAN_NAME_FIELD));
		assertEquals(BEAN_ID_VALUE, context.read("$." + BEAN_ID_FIELD));
	}

	/**
	 * Test method for {@link JacksonUtils#toXMLSelectFields(Object, String[])}.
	 */
	@Test
	void testToXMLSelectFields() {
		assertThat(JacksonUtils.toXMLSelectFields(testBean, BEAN_ID_FIELD)).doesNotContain(BEAN_NAME_FIELD)
				.contains(BEAN_ID_FIELD + "=\"");
	}

	/**
	 * Test method for {@link JacksonUtils#toYAMLSelectFields(Object, String[])}.
	 */
	@Test
	void testToYAMLSelectFields() {
		assertThat(JacksonUtils.toYAMLSelectFields(testBean, BEAN_ID_FIELD)).doesNotContain(BEAN_NAME_FIELD)
				.contains(BEAN_ID_FIELD + ": \"");
	}

	/**
	 * Test method for {@link JacksonUtils#fromJSON(String, Class)}.
	 */
	@Test
	void testFromJSON() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromJSON(testBean.toJSON(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for {@link JacksonUtils#fromXML(String, Class)}.
	 */
	@Test
	void testFromXML() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromXML(testBean.toXML(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for {@link JacksonUtils#fromYAML(String, Class)}.
	 */
	@Test
	void testFromYAML() {
		assertEquals(BEAN_ID_VALUE, JacksonUtils.fromYAML(testBean.toYAML(), TestDataBean.class).getBeanId());
	}

	/**
	 * Test method for {@link JacksonUtils#buildJSON(Object...)}.
	 */
	@Test
	void testBuildJSONObjectArray() {
		assertEquals(VALUE, JsonPath.parse(JacksonUtils.buildJSON(KEY, VALUE)).read("$.key"));
	}

	/**
	 * Test method for {@link JacksonUtils#buildXML(String, Object[])}.
	 */
	@Test
	void testBuildXMLObjectArray() {
		assertEquals(VALUE, XMLUtils.xPathNodeValue("/BuildXML/key",
				XMLUtils.stringToXML(JacksonUtils.buildXML("BuildXML", KEY, VALUE))));
	}

	/**
	 * Test method for {@link JacksonUtils#buildYAML(Object[])}.
	 */
	@Test
	void testBuildYAMLObjectArray() {
		assertThat(JacksonUtils.buildYAML(KEY, VALUE)).contains("key: \"value\"");
	}
}
