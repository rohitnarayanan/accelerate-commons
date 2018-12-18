package accelerate.commons.utils;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 22, 2018
 */
public class DateTimeUtils {
	/**
	 * @param aMilliseconds
	 * @return
	 */
	public static final String convertToTime(long aMilliseconds) {
		int baseValue = 60 * 60 * 1000;
		long hours = (aMilliseconds / baseValue);
		long balance = (aMilliseconds % baseValue);

		baseValue = 60 * 1000;
		long minutes = (balance / baseValue);
		balance = (balance % baseValue);

		long seconds = (balance / 1000);

		return ((hours < 1) ? "" : hours + " hours ") + ((minutes < 1) ? "" : minutes + " minutes ")
				+ (seconds + " seconds");
	}
}
