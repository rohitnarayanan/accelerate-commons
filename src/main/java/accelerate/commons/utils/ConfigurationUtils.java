package accelerate.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

import accelerate.commons.constants.CommonConstants;
import accelerate.commons.data.DataMap;
import accelerate.commons.exceptions.ApplicationException;

/**
 * Class providing common utility methods
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 20, 2018
 */
public final class ConfigurationUtils {
	/**
	 * hidden constructor
	 */
	private ConfigurationUtils() {
	}

	/**
	 * @param aConfigPath
	 * @return
	 */
	public static DataMap<String> loadPropertyFile(String aConfigPath) {
		if (StringUtils.isEmpty(aConfigPath)) {
			throw new ApplicationException("Parameter ConfigPath is required");
		}

		String configPath = StringUtils.safeTrim(aConfigPath);
		URL resourceURL = null;
		if (configPath.startsWith(CommonConstants.UNIX_PATH_CHAR)) {
			resourceURL = ConfigurationUtils.class.getResource(configPath);
		} else if (configPath.startsWith("classpath:")) {
			resourceURL = ConfigurationUtils.class.getResource(configPath.substring(10));
		} else {
			try {
				resourceURL = new URL(configPath);
			} catch (MalformedURLException error) {
				throw new ApplicationException(error);
			}
		}

		Properties properties = new Properties();
		DataMap<String> configMap = new DataMap<>();
		try (InputStream inputStream = resourceURL.openStream()) {
			properties.load(inputStream);
			properties.forEach((aKey, aValue) -> configMap.put(aKey.toString(), aValue.toString()));
		} catch (IOException error) {
			throw new ApplicationException(error);
		}

		return configMap;
	}

	/**
	 * @param aConfigPath
	 * @return
	 */
	public static DataMap<String> loadYAMLFile(String aConfigPath) {
		if (StringUtils.isEmpty(aConfigPath)) {
			throw new ApplicationException("Parameter ConfigPath is required");
		}

		String configPath = StringUtils.safeTrim(aConfigPath);
		URL resourceURL = null;
		if (configPath.startsWith(CommonConstants.UNIX_PATH_CHAR)) {
			resourceURL = ConfigurationUtils.class.getResource(configPath);
		} else if (configPath.startsWith("classpath:")) {
			resourceURL = ConfigurationUtils.class.getResource(configPath.substring(10));
		} else {
			try {
				resourceURL = new URL(configPath);
			} catch (MalformedURLException error) {
				throw new ApplicationException(error);
			}
		}

		Yaml yaml = new Yaml();
		DataMap<String> configMap = new DataMap<>();
		try (InputStream inputStream = resourceURL.openStream()) {
			Map<String, Object> yamlMap = yaml.loadAs(inputStream, Map.class);
			yamlToProperties(yamlMap, CommonConstants.EMPTY_STRING, configMap);
		} catch (IOException error) {
			throw new ApplicationException(error);
		}

		return configMap;
	}

	/**
	 * @param aYAMLMap
	 * @param aPrefix
	 * @param aConfigMap
	 */
	private static void yamlToProperties(Map<String, Object> aYAMLMap, String aPrefix, DataMap<String> aConfigMap) {
		aYAMLMap.entrySet().forEach(aEntry -> {
			String key = aPrefix + aEntry.getKey();
			Object value = aEntry.getValue();
			if (value instanceof Map) {
				yamlToProperties(Map.class.cast(value), key + CommonConstants.DOT_CHAR, aConfigMap);
			} else {
				aConfigMap.put(key, value.toString());
			}
		});
	}
}