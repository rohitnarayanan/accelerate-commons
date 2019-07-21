package accelerate.commons.util;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import accelerate.commons.data.DataMap;
import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for JSON operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class JacksonUtils {
	/**
	 * Default {@link ObjectMapper} to be used for all JSON serialization
	 */
	private static ObjectMapper jsonMapper = configureMapper(new ObjectMapper());

	/**
	 * Default {@link ObjectMapper} to be used for all JSON serialization
	 */
	private static XmlMapper xmlMapper = configureMapper(new XmlMapper());

	/**
	 * Default {@link YAMLMapper} to be used for all JSON serialization
	 */
	private static YAMLMapper yamlMapper = configureMapper(new YAMLMapper());

	/**
	 * This method returns the default instance of {@link ObjectMapper}
	 *
	 * @return
	 */
	public static ObjectMapper objectMapper() {
		return configureMapper(new ObjectMapper());
	}

	/**
	 * This method returns the default instance of {@link XmlMapper}
	 *
	 * @return
	 */
	public static XmlMapper xmlMapper() {
		return configureMapper(new XmlMapper());
	}

	/**
	 * This method returns the default instance of {@link XmlMapper}
	 *
	 * @return
	 */
	public static YAMLMapper yamlMapper() {
		return configureMapper(new YAMLMapper());
	}

	/**
	 * This method returns an instance of {@link ObjectMapper} based on the given
	 * flags.
	 * 
	 * @param <T>     {@link ObjectMapper} or subtype like {@link XmlMapper} /
	 *                {@link YAMLMapper}
	 * 
	 * @param aMapper
	 * @return
	 */
	public static <T extends ObjectMapper> T configureMapper(T aMapper) {

		aMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		aMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		// Write JSON settings
		aMapper.setDefaultPropertyInclusion(Include.NON_NULL);
		aMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		aMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		aMapper.configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, false);

		// Read JSON settings
		aMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		return aMapper;
	}

	/**
	 * This is the default method to convert the given object to JSON string
	 *
	 * @param aObject Object to be converted to JSON string
	 * @return JSON string
	 * @throws ApplicationException
	 */
	public static String toJSON(Object aObject) throws ApplicationException {
		return serialize(aObject, jsonMapper);
	}

	/**
	 * This is the default method to convert the given object to XML string
	 *
	 * @param aObject Object to be converted to XML string
	 * @return XML string
	 * @throws ApplicationException
	 */
	public static String toXML(Object aObject) throws ApplicationException {
		return serialize(aObject, xmlMapper);
	}

	/**
	 * This is the default method to convert the given object to YAML string
	 *
	 * @param aObject Object to be converted to YAML string
	 * @return YAML string
	 * @throws ApplicationException
	 */
	public static String toYAML(Object aObject) throws ApplicationException {
		return serialize(aObject, yamlMapper);
	}

	/**
	 * This is method serializes the given object using the mapper instance
	 * provided. It is common for JSON/XML/YAML converion
	 * 
	 * @param <T>     {@link ObjectMapper} or subtype like {@link XmlMapper} /
	 *                {@link YAMLMapper}
	 *
	 * @param aObject
	 * @param aMapper
	 * @return
	 * @throws ApplicationException
	 */
	private static <T extends ObjectMapper> String serialize(Object aObject, T aMapper) throws ApplicationException {
		if (aObject == null) {
			return EMPTY_STRING;
		}

		try {
			String stringValue = aMapper.writeValueAsString(aObject);
			LOGGER.trace("serialize: {} | {}", aObject, stringValue);

			return stringValue;
		} catch (JsonProcessingException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * This method converts the given object to JSON string, excluding all the given
	 * field names
	 *
	 * @param aObject         Object to be converted to JSON string
	 * @param aExcludedFields Fields to be excluded from the JSON string
	 * @return JSON string
	 * @throws ApplicationException
	 */
	public static String toJSONExcludeFields(Object aObject, String... aExcludedFields) throws ApplicationException {
		return serializeExcept(aObject, jsonMapper.copy(), aExcludedFields);
	}

	/**
	 * This method converts the given object to XML string, excluding all the given
	 * field names
	 *
	 * @param aObject         Object to be converted to XML string
	 * @param aExcludedFields Fields to be excluded from the XML string
	 * @return XML string
	 * @throws ApplicationException
	 */
	public static String toXMLExcludeFields(Object aObject, String... aExcludedFields) throws ApplicationException {
		return serializeExcept(aObject, xmlMapper.copy(), aExcludedFields);
	}

	/**
	 * This method converts the given object to YAML string, excluding all the given
	 * field names
	 *
	 * @param aObject         Object to be converted to YAML string
	 * @param aExcludedFields Fields to be excluded from the YAML string
	 * @return YAML string
	 * @throws ApplicationException
	 */
	public static String toYAMLExcludeFields(Object aObject, String... aExcludedFields) throws ApplicationException {
		return serializeExcept(aObject, yamlMapper.copy(), aExcludedFields);
	}

	/**
	 * This is method serializes the given object using the mapper instance
	 * provided. It is common for JSON/XML/YAML converion
	 * 
	 * @param <T>             {@link ObjectMapper} or subtype like {@link XmlMapper}
	 *                        / {@link YAMLMapper}
	 *
	 * @param aObject
	 * @param aMapper
	 * @param aExcludedFields Field names to be excluded
	 * @return
	 * @throws ApplicationException
	 */
	private static <T extends ObjectMapper> String serializeExcept(Object aObject, T aMapper, String... aExcludedFields)
			throws ApplicationException {
		if (aObject == null) {
			return EMPTY_STRING;
		}

		try {
			aMapper.addMixIn(aObject.getClass(), PropertyFilterMixIn.class);
			String jsonString = aMapper
					.writer(new SimpleFilterProvider()
							.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(aExcludedFields)))
					.writeValueAsString(aObject);
			LOGGER.trace("serializeExcept: {} | {} | {}", aObject, aExcludedFields, jsonString);

			return jsonString;
		} catch (JsonProcessingException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * This method converts the given object to JSON string, including only the
	 * given field names
	 *
	 * @param aObject         Object to be converted to JSON string
	 * @param aIncludedFields Field names that should be included in the JSON output
	 * @return JSON string
	 * @throws ApplicationException
	 */
	public static String toJSONSelectFields(Object aObject, String... aIncludedFields) throws ApplicationException {
		return serializeOnly(aObject, jsonMapper.copy(), aIncludedFields);
	}

	/**
	 * This method converts the given object to JSON string, including only the
	 * given field names
	 *
	 * @param aObject         Object to be converted to JSON string
	 * @param aIncludedFields Field names that should be included in the JSON output
	 * @return JSON string
	 * @throws ApplicationException
	 */
	public static String toXMLSelectFields(Object aObject, String... aIncludedFields) throws ApplicationException {
		return serializeOnly(aObject, xmlMapper.copy(), aIncludedFields);
	}

	/**
	 * This method converts the given object to JSON string, including only the
	 * given field names
	 *
	 * @param aObject         Object to be converted to JSON string
	 * @param aIncludedFields Field names that should be included in the JSON output
	 * @return JSON string
	 * @throws ApplicationException
	 */
	public static String toYAMLSelectFields(Object aObject, String... aIncludedFields) throws ApplicationException {
		return serializeOnly(aObject, yamlMapper.copy(), aIncludedFields);
	}

	/**
	 * This is method serializes the given object using the mapper instance
	 * provided. It is common for JSON/XML/YAML converion
	 * 
	 * @param <T>             {@link ObjectMapper} or subtype like {@link XmlMapper}
	 *                        / {@link YAMLMapper}
	 *
	 * @param aObject
	 * @param aMapper
	 * @param aIncludedFields Field names to be included
	 * @return
	 * @throws ApplicationException
	 */
	private static <T extends ObjectMapper> String serializeOnly(Object aObject, T aMapper, String... aIncludedFields)
			throws ApplicationException {
		if (aObject == null) {
			return EMPTY_STRING;
		}

		try {
			aMapper.addMixIn(aObject.getClass(), PropertyFilterMixIn.class);
			String jsonString = aMapper
					.writer(new SimpleFilterProvider()
							.setDefaultFilter(SimpleBeanPropertyFilter.filterOutAllExcept(aIncludedFields)))
					.writeValueAsString(aObject);
			LOGGER.trace("serializeOnly: {} | {} | {}", aObject, aIncludedFields, jsonString);

			return jsonString;
		} catch (JsonProcessingException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * This method parses the given JSON string and returns an instance of the given
	 * class loaded with data
	 *
	 * @param <T>         Any subclass of {@link Object}
	 * @param aJSONString JSON string to be parsed
	 * @param aClass      {@link Class} which should be instantiated from the JSON
	 *                    string
	 * @return loaded instance
	 * @throws ApplicationException
	 */
	public static <T> T fromJSON(String aJSONString, Class<T> aClass) throws ApplicationException {
		return deserialize(aJSONString, aClass, jsonMapper);
	}

	/**
	 * This method parses the given JSON string and returns an instance of the given
	 * class loaded with data
	 *
	 * @param <T>         Any subclass of {@link Object}
	 * @param aJSONString JSON string to be parsed
	 * @param aClass      {@link Class} which should be instantiated from the JSON
	 *                    string
	 * @return loaded instance
	 * @throws ApplicationException
	 */
	public static <T> T fromXML(String aJSONString, Class<T> aClass) throws ApplicationException {
		return deserialize(aJSONString, aClass, xmlMapper);
	}

	/**
	 * This method parses the given JSON string and returns an instance of the given
	 * class loaded with data
	 *
	 * @param <C>         Any subclass of {@link Object}
	 * @param aJSONString JSON string to be parsed
	 * @param aClass      {@link Class} which should be instantiated from the JSON
	 *                    string
	 * @return loaded instance
	 * @throws ApplicationException
	 */
	public static <C> C fromYAML(String aJSONString, Class<C> aClass) throws ApplicationException {
		return deserialize(aJSONString, aClass, yamlMapper);
	}

	/**
	 * This is method deserializes the given object using the mapper instance
	 * provided. It is common for JSON/XML/YAML parsing
	 *
	 * @param <C>     Any subclass of {@link Object}
	 * @param <T>     {@link ObjectMapper} or subtype like {@link XmlMapper} /
	 *                {@link YAMLMapper}
	 * @param aString
	 * @param aClass  {@link Class} which should be instantiated from the string
	 * @param aMapper
	 * @return
	 * @throws ApplicationException
	 */
	private static <C extends Object, T extends ObjectMapper> C deserialize(String aString, Class<C> aClass, T aMapper)
			throws ApplicationException {
		if (StringUtils.isEmpty(aString)) {
			return null;
		}

		try {
			return aMapper.readValue(aString, aClass);
		} catch (IOException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aArgs
	 * @return
	 * @throws ApplicationException
	 */
	public static String buildJSON(Object... aArgs) throws ApplicationException {
		return serialize(DataMap.newMap(aArgs), jsonMapper);
	}

	/**
	 * @param aArgs
	 * @return
	 * @throws ApplicationException
	 */
	public static String buildXML(Object... aArgs) throws ApplicationException {
		return serialize(DataMap.newMap(aArgs), xmlMapper);
	}

	/**
	 * @param aArgs
	 * @return
	 * @throws ApplicationException
	 */
	public static String buildYAML(Object... aArgs) throws ApplicationException {
		return serialize(DataMap.newMap(aArgs), yamlMapper);
	}

	/**
	 * Empty class to enable {@link PropertyFilter} to allow partial serializing
	 * 
	 * @version 1.0 Initial Version
	 * @author Rohit Narayanan
	 * @since July 17, 2019
	 */
	@JsonFilter("PropertyFilter")
	private abstract class PropertyFilterMixIn {
		// empty class
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtils.class);

	/**
	 * hidden constructor
	 */
	private JacksonUtils() {
	}
}