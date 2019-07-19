package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link JSONUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
public class JSONUtilsTests {
	/**
	 * Test method for {@link JSONUtils#objectMapper()}.
	 */
	@Test
	void testObjectMapper() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link JSONUtils#objectMapper(com.fasterxml.jackson.annotation.JsonInclude.Include, boolean, boolean, boolean, boolean)}.
	 */
	@Test
	void testObjectMapperIncludeBooleanBooleanBooleanBoolean() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link JSONUtils#configureSpringObjectMapper(com.fasterxml.jackson.databind.ObjectMapper)}.
	 */
	@Test
	void testConfigureSpringObjectMapper() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#serialize(Object)}.
	 */
	@Test
	void testSerialize() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#serializeExcept(Object, String[])}.
	 */
	@Test
	void testSerializeExcept() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#serializeOnly(Object, String[])}.
	 */
	@Test
	void testSerializeOnly() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#deserialize(String, Class)}.
	 */
	@Test
	void testDeserialize() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#buildJSON(Object...)}.
	 */
	@Test
	void testBuildJSONStringObject() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link JSONUtils#buildJSON(java.util.Map)}.
	 */
	@Test
	void testBuildJSONMapOfQQ() {
		assertTrue(true);
	}
}