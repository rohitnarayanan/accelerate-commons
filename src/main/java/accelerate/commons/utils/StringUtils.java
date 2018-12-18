package accelerate.commons.utils;

import static accelerate.commons.constants.CommonConstants.EMPTY_STRING;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class providing utility methods for {@link String} operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 20, 2018
 */
public final class StringUtils {
	/**
	 * hidden constructor
	 */
	private StringUtils() {
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static boolean isEmpty(CharSequence aString) {
		return safeTrim(aString).length() == 0;
	}

	/**
	 * @param aString
	 * @param aDefaultString
	 * @return
	 */
	public static String defaultString(CharSequence aString, String aDefaultString) {
		if (aString != null) {
			return aString.toString();
		}

		return aDefaultString;
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static int safeLength(CharSequence aString) {
		if (aString != null) {
			return aString.length();
		}

		return 0;
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeTrim(CharSequence aString) {
		if (aString != null) {
			return aString.toString().trim();
		}

		return EMPTY_STRING;
	}

	/**
	 * @param aString1
	 * @param aString2
	 * @return true if Strings are equal
	 */
	public static boolean safeEquals(String aString1, String aString2) {
		if ((aString1 == null) || (aString2 == null)) {
			return false;
		}

		return aString1.equals(aString2);
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeToUpper(CharSequence aString) {
		return safeTrim(aString).toUpperCase();
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeToLower(CharSequence aString) {
		return safeTrim(aString).toLowerCase();
	}

	/**
	 * @param aString
	 * @return toString()
	 */
	public static String safeCapitalize(CharSequence aString) {
		int length = safeLength(aString);
		if (length == 0) {
			return EMPTY_STRING;
		}

		String value = aString.toString();

		if (length == 1) {
			return value.toUpperCase();
		}

		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}

	/**
	 * @param aString
	 * @param aDelimiter
	 * @return toString()
	 */
	public static String[] safeSplit(CharSequence aString, String aDelimiter) {
		return safeTrim(aString).split(aDelimiter);
	}

	/**
	 * @param aInputString
	 * @param aRecordDelim
	 * @param aFieldDelim
	 * @return array of delimited values
	 */
	public static Map<String, String> multiSplit(String aInputString, String aRecordDelim, String aFieldDelim) {
		if (isEmpty(aInputString)) {
			return Collections.emptyMap();
		}

		return Arrays.stream(aInputString.split(aRecordDelim)).map(aLine -> aLine.split(aFieldDelim)).collect(
				Collectors.toMap(aValues -> aValues[0], aValues -> (aValues.length == 1) ? aValues[0] : aValues[1]));
	}
}