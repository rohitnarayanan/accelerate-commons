package accelerate.commons;

import accelerate.commons.data.DataMap;
import accelerate.commons.utils.ConfigurationUtils;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since December 17, 2018
 */
public class AccelerateCommonsTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DataMap<String> dataMap = ConfigurationUtils.loadYAMLFile("/application.yml");
			System.out.println(dataMap.get("test.servers"));

			dataMap = ConfigurationUtils.loadPropertyFile("/temp.properties");
			System.out.println(dataMap.get("test.servers[0]"));
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
}
