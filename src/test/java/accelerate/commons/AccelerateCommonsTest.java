package accelerate.commons;

import static accelerate.commons.constant.CommonTestConstants.KEY;
import static accelerate.commons.constant.CommonTestConstants.VALUE;

import accelerate.commons.data.DataBean;
import accelerate.commons.data.DataMap;
import accelerate.commons.data.TestDataBean;
import accelerate.commons.exception.ApplicationException;

/**
 * Basic test class for this project
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class AccelerateCommonsTest {
	/**
	 * {@link DataMap} for this test class
	 */
	public static final DataMap testDataMap = DataMap.newMap(KEY, VALUE);

	/**
	 * {@link DataMap} for this test class
	 */
	public static final DataBean testDataBean = new TestDataBean().putAnd(KEY, VALUE);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(new ApplicationException(null, null, false, false).getSuppressed().length);
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
}
