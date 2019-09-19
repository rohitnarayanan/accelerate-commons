package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link DateTimeUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class DateTimeUtilsTests {
	/**
	 * Test method for
	 * {@link DateTimeUtils#parseDate(String, String, Locale, String)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	void testParseDate() throws ParseException {
		// empty date string
		assertNull(DateTimeUtils.parseDate(null, null, null, null));

		// only date
		Date sample = new Date();
		String sampleString = new SimpleDateFormat().format(sample);
		assertEquals(new SimpleDateFormat().parse(sampleString).getTime(),
				DateTimeUtils.parseDate(sampleString, null, null, null).getTime());

		// with format
		String format = "MM/dd/yyyy HH:ss:SSS z";
		sampleString = new SimpleDateFormat(format).format(sample);
		assertEquals(new SimpleDateFormat(format).parse(sampleString).getTime(),
				DateTimeUtils.parseDate(sampleString, format, null, null).getTime());

		// with locale
		Locale locale = Locale.FRANCE;
		sampleString = new SimpleDateFormat(format, locale).format(sample);
		assertEquals(new SimpleDateFormat(format, locale).parse(sampleString).getTime(),
				DateTimeUtils.parseDate(sampleString, format, locale, null).getTime());

		// with timezone
		TimeZone timezone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
		formatter.setTimeZone(timezone);
		sampleString = formatter.format(sample);
		assertEquals(formatter.parse(sampleString).getTime(),
				DateTimeUtils.parseDate(sampleString, format, locale, "UTC").getTime());
	}

	/**
	 * Test method for
	 * {@link DateTimeUtils#formatDate(Date, String, Locale, String)}.
	 */
	@Test
	void testFormatDate() {
		// null date
		assertEquals(0, DateTimeUtils.formatDate(null, null, null, null).length());

		// only date
		Date sample = new Date();
		assertEquals(new SimpleDateFormat().format(sample), DateTimeUtils.formatDate(sample, null, null, null));

		// with format
		String format = "MM/dd/yyyy HH:ss:SSS z";
		assertEquals(new SimpleDateFormat(format).format(sample), DateTimeUtils.formatDate(sample, format, null, null));

		// with locale
		Locale locale = Locale.FRANCE;
		assertEquals(new SimpleDateFormat(format, locale).format(sample),
				DateTimeUtils.formatDate(sample, format, locale, null));

		// with timezone
		TimeZone timezone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
		formatter.setTimeZone(timezone);
		assertEquals(formatter.format(sample), DateTimeUtils.formatDate(sample, format, locale, "UTC"));
	}

	/**
	 * Test method for {@link DateTimeUtils#convertToTime(long)}.
	 */
	@Test
	void testConvertToTime() {
		// test seconds only
		assertEquals("0 hours, 0 minutes, 2 seconds", DateTimeUtils.convertToTime(2500));

		// test minutes and seconds
		assertEquals("0 hours, 2 minutes, 2 seconds", DateTimeUtils.convertToTime((1000 * 60 * 2) + (2000)));

		// test minutes only
		assertEquals("0 hours, 2 minutes, 0 seconds", DateTimeUtils.convertToTime((1000 * 60 * 2)));

		// test hours, minutes, and seconds
		assertEquals("2 hours, 2 minutes, 2 seconds",
				DateTimeUtils.convertToTime((1000 * 60 * 60 * 2) + (1000 * 60 * 2) + (2000)));

		// test hours and minutes
		assertEquals("2 hours, 2 minutes, 0 seconds",
				DateTimeUtils.convertToTime((1000 * 60 * 60 * 2) + (1000 * 60 * 2)));

		// test hours and seconds
		assertEquals("2 hours, 0 minutes, 2 seconds", DateTimeUtils.convertToTime((1000 * 60 * 60 * 2) + (2000)));

		// test hours only
		assertEquals("2 hours, 0 minutes, 0 seconds", DateTimeUtils.convertToTime((1000 * 60 * 60 * 2)));
	}
}