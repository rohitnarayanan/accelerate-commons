package accelerate.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for Date/Time operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class DateTimeUtils {
	/**
	 * @param aDateString
	 * @param aFormat
	 * @param aLocale
	 * @param aTimezone
	 * @return
	 */
	public static final Date parseDate(String aDateString, String aFormat, Locale aLocale, String aTimezone) {
		if (StringUtils.isEmpty(aDateString)) {
			return null;
		}

		SimpleDateFormat formatter = StringUtils.isEmpty(aFormat) ? new SimpleDateFormat()
				: (aLocale == null) ? new SimpleDateFormat(aFormat) : new SimpleDateFormat(aFormat, aLocale);

		if (!StringUtils.isEmpty(aTimezone)) {
			formatter.setTimeZone(TimeZone.getTimeZone(aTimezone));
		}

		try {
			return formatter.parse(aDateString);
		} catch (ParseException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aDate
	 * @param aFormat
	 * @param aLocale
	 * @param aTimezone
	 * @return
	 */
	public static final String formatDate(Date aDate, String aFormat, Locale aLocale, String aTimezone) {
		if (aDate == null) {
			return CommonConstants.EMPTY_STRING;
		}

		SimpleDateFormat formatter = StringUtils.isEmpty(aFormat) ? new SimpleDateFormat()
				: (aLocale == null) ? new SimpleDateFormat(aFormat) : new SimpleDateFormat(aFormat, aLocale);

		if (!StringUtils.isEmpty(aTimezone)) {
			formatter.setTimeZone(TimeZone.getTimeZone(aTimezone));
		}

		return formatter.format(aDate);
	}

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
