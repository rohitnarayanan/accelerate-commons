package accelerate.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import accelerate.commons.constants.CommonConstants;

/**
 * Class providing common utility methods
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 20, 2018
 */
public final class CommonUtils {
	/**
	 * hidden constructor
	 */
	private CommonUtils() {
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String safeToString(Object aValue) {
		if (aValue == null) {
			return CommonConstants.EMPTY_STRING;
		}

		// else
		return aValue.toString();
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static boolean isPrimitive(Object aValue) {
		if (aValue == null) {
			return false;
		}

		if (aValue instanceof String || aValue instanceof Number) {
			return true;
		}

		return false;
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static boolean isEmpty(Object aValue) {
		if (aValue == null) {
			return true;
		}

		if (aValue instanceof Optional) {
			return !((Optional<?>) aValue).isPresent();
		}

		if (aValue instanceof CharSequence) {
			return ((CharSequence) aValue).length() == 0;
		}

		if (aValue.getClass().isArray()) {
			return Array.getLength(aValue) == 0;
		}

		if (aValue instanceof Collection) {
			return ((Collection<?>) aValue).isEmpty();
		}

		if (aValue instanceof Map) {
			return ((Map<?, ?>) aValue).isEmpty();
		}

		// else
		return false;
	}

	/**
	 * @param aValueList
	 * @return
	 */
	public static boolean isEmptyAny(Object... aValueList) {
		if (aValueList == null) {
			return true;
		}

		for (Object value : aValueList) {
			if (isEmpty(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param aValueList
	 * @return
	 */
	public static boolean isEmptyAll(Object... aValueList) {
		if (aValueList == null) {
			return true;
		}

		for (Object value : aValueList) {
			if (isEmpty(value)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param aValue1 Left side value
	 * @param aValue2 Right side value
	 * @return true if values are true mutually exclusive
	 */
	public static boolean xor(boolean aValue1, boolean aValue2) {
		if (aValue1) {
			return !aValue2;
		}

		return aValue2;
	}

	/**
	 * @param        <T>
	 * @param value1 Left side value
	 * @param value2 Right side value
	 * @return true if objects are equal
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean compare(T value1, T value2) {
		if ((value1 == null) || (value2 == null)) {
			return false;
		}

		if (value1 instanceof Comparable<?>) {
			return ((Comparable<T>) value1).compareTo(value2) == 0;
		}

		return value1.equals(value2);
	}

	/**
	 * @param                   <T>
	 * @param aCompareValue     Left side value
	 * @param aCompareValueList List of values to be compared with
	 * @return true if any of the compare values matches the leftValue
	 */
	@SafeVarargs
	public static <T> boolean compareAny(T aCompareValue, T... aCompareValueList) {
		if (aCompareValue == null || aCompareValueList == null) {
			return false;
		}

		for (T compareValue : aCompareValueList) {
			if (compare(aCompareValue, compareValue)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param aError
	 * @return error log
	 */
	public static String getErrorMessage(Throwable aError) {
		if (aError == null) {
			return CommonConstants.EMPTY_STRING;
		}

		String message = aError.getMessage();
		return StringUtils.isEmpty(message) ? aError.getClass().getName() : message;
	}

	/**
	 * @param aError
	 * @return error log
	 */
	public static String getErrorLog(Throwable aError) {
		if (aError == null) {
			return CommonConstants.EMPTY_STRING;
		}

		StringWriter writer = new StringWriter();
		aError.printStackTrace(new PrintWriter(writer));
		writer.flush();

		return writer.getBuffer().toString();
	}
}