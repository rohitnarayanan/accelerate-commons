package accelerate.commons.util;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;

/**
 * Class providing utility methods for {@link String} operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class StringUtils {
	/**
	 * {@link String#trim()} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @return
	 */
	public static String trim(CharSequence aString) {
		if (aString != null) {
			return aString.toString().trim();
		}

		return EMPTY_STRING;
	}

	/**
	 * {@link String#length()} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @return
	 */
	public static int length(CharSequence aString) {
		return trim(aString).length();
	}

	/**
	 * {@link String#equals(Object)} shortcut to handle null/blank inputs
	 * 
	 * @param aStringA
	 * @param aStringB
	 * @return
	 */
	public static boolean equals(CharSequence aStringA, CharSequence aStringB) {
		return trim(aStringA).equals(trim(aStringB));
	}

	/**
	 * {@link String#isEmpty()} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @return
	 */
	public static boolean isEmpty(CharSequence aString) {
		return trim(aString).isEmpty();
	}

	/**
	 * {@link String#toUpperCase()} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @return
	 */
	public static String toUpperCase(CharSequence aString) {
		return trim(aString).toUpperCase();
	}

	/**
	 * {@link String#toLowerCase()} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @return
	 */
	public static String toLowerCase(CharSequence aString) {
		return trim(aString).toLowerCase();
	}

	/**
	 * {@link String#split(String)} shortcut to handle null/blank inputs
	 * 
	 * @param aString
	 * @param aDelimiter
	 * @return
	 */
	public static String[] split(CharSequence aString, String aDelimiter) {
		return trim(aString).split(aDelimiter);
	}

	/**
	 * @param aString
	 * @param aDefaultString
	 * @return
	 */
	public static String defaultString(CharSequence aString, CharSequence aDefaultString) {
		if (isEmpty(aString)) {
			return aDefaultString.toString();
		}

		return aString.toString();
	}

	/**
	 * @param aString
	 * @return
	 */
	public static String capitalize(CharSequence aString) {
		String value = trim(aString);

		if (isEmpty(value)) {
			return EMPTY_STRING;
		}

		if (value.length() == 1) {
			return value.toUpperCase();
		}

		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}

	/**
	 * hidden constructor
	 */
	private StringUtils() {
	}
}