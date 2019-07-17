package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * {@link Test} class for {@link DateTimeUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
public class DateTimeUtilsTests {
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