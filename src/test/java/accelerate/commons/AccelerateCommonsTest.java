package accelerate.commons;

import accelerate.commons.data.DataMap;
import accelerate.commons.util.ConfigurationUtils;

/**
 * Basic test class for this project
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class AccelerateCommonsTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DataMap dataMap = ConfigurationUtils.loadYAMLFile("/accelerate/commons/util/ConfigurationUtilsTest.yml");
			System.out.println(dataMap.getString("test.list"));

			dataMap = ConfigurationUtils.loadPropertyFile("/temp.properties");
			System.out.println(dataMap.getString("test.servers[0]"));
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
}
