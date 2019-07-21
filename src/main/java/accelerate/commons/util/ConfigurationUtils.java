package accelerate.commons.util;

import java.io.IOException;
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

		final DataMap configMap = DataMap.newMap();
		return StreamUtils.loadInputStream(aConfigPath, aInputStream -> {
			try {
				Properties props = new Properties();
				props.load(aInputStream);
				props.forEach((aKey, aValue) -> configMap.put(aKey.toString(), aValue.toString()));

				return configMap;
			} catch (IOException error) {
				throw new ApplicationException(error);
			}
		});
	}

	/**
	 * @param aConfigPath
	 * @return
	 */
	public static DataMap loadYAMLFile(String aConfigPath) {
		if (StringUtils.isEmpty(aConfigPath)) {
			throw new ApplicationException("Parameter ConfigPath is required");
		}

		final DataMap configMap = DataMap.newMap();
		final Yaml yaml = new Yaml();
		return StreamUtils.loadInputStream(aConfigPath, aInputStream -> {
			Map<String, Object> yamlMap = yaml.loadAs(aInputStream, Map.class);
			yamlToProperties(yamlMap, CommonConstants.EMPTY_STRING, configMap);

			return configMap;
		});
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