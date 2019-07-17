package accelerate.commons.util;

/**
 * Class providing utility methods for Date/Time operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class DateTimeUtils {
	/**
	 * @param aMilliseconds
	 * @return
	 */
	public static final String convertToTime(long aMilliseconds) {
		int hourMilliseconds = 60 * 60 * 1000;
		long hours = (aMilliseconds / hourMilliseconds);
		long balance = (aMilliseconds % hourMilliseconds);

		int minuteMilliseconds = 60 * 1000;
		long minutes = (balance / minuteMilliseconds);
		balance = (balance % minuteMilliseconds);

		long seconds = (balance / 1000);

		return String.format("%s hours, %s minutes, %s seconds", hours, minutes, seconds);
	}

	/**
	 * hidden constructor
	 */
	private DateTimeUtils() {
	}
}
