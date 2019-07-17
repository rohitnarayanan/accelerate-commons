package accelerate.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.data.DataMap;
import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for reading configuration files
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class ConfigurationUtils {
	/**
	 * @param aConfigPath
	 * @return
	 */
	public static DataMap loadPropertyFile(String aConfigPath) {
		if (StringUtils.isEmpty(aConfigPath)) {
			throw new ApplicationException("Parameter ConfigPath is required");
		}

		String configPath = StringUtils.trim(aConfigPath);
		URL resourceURL = null;
		if (configPath.startsWith(CommonConstants.UNIX_PATH_SEPARATOR)) {
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
		DataMap configMap = DataMap.newMap();
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
	public static DataMap loadYAMLFile(String aConfigPath) {
		if (StringUtils.isEmpty(aConfigPath)) {
			throw new ApplicationException("Parameter ConfigPath is required");
		}

		String configPath = StringUtils.trim(aConfigPath);
		URL resourceURL = null;
		if (configPath.startsWith(CommonConstants.UNIX_PATH_SEPARATOR)) {
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
		DataMap configMap = DataMap.newMap();
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
	private static void yamlToProperties(Map<String, Object> aYAMLMap, String aPrefix, DataMap aConfigMap) {
		aYAMLMap.entrySet().forEach(aEntry -> {
			String key = aPrefix + aEntry.getKey();
			Object value = aEntry.getValue();
			if (value instanceof Map) {
				yamlToProperties(Map.class.cast(value), key + CommonConstants.PERIOD, aConfigMap);
			} else {
				aConfigMap.put(key, value.toString());
			}
		});
	}

	/**
	 * hidden constructor
	 */
	private ConfigurationUtils() {
	}
}