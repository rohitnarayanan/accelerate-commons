package accelerate.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.constant.CommonConstants;

/**
 * Class providing common utility methods
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class CommonUtils {
	/**
	 * Utility method to adjust the start index for all sub*** methods for
	 * Lists/Strings etc.
	 * 
	 * @param aIndex
	 * @param aLength
	 * @return
	 */
	public static int getAdjustedIndex(int aIndex, int aLength) {
		int adjustedIndex = (aIndex < 0) ? (aLength + aIndex) : aIndex;
		if (adjustedIndex < 0) {
			return 0;
		}

		if (adjustedIndex > aLength) {
			return aLength;
		}

		return adjustedIndex;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Optional) {
			return !((Optional<?>) obj).isPresent();
		}

		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}

		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}

		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}

		// else
		return false;
	}

	/**
	 * This method check if any of the input parameters is null or empty
	 * 
	 * @param aValueList
	 * @return
	 */
	public static boolean isEmptyAny(Object... aValueList) {
		if (isEmpty(aValueList)) {
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
	 * This method check if <b>all</b> of the input parameters are null or empty
	 * 
	 * @param aValueList
	 * @return
	 */
	public static boolean isEmptyAll(Object... aValueList) {
		if (isEmpty(aValueList)) {
			return true;
		}

		for (Object value : aValueList) {
			if (!isEmpty(value)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * This method performs an XOR and returns true only when one of the input
	 * parameter is true
	 * 
	 * @param aValue1
	 * @param aValue2
	 * @return
	 */
	public static boolean xor(boolean aValue1, boolean aValue2) {
		if (aValue1) {
			return !aValue2;
		}

		return aValue2;
	}

	/**
	 * @param <T>
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
	 * This method checks if the given value compares to any of the values in the
	 * provided array. It converts the array to list using
	 * {@link CollectionUtils#toList(Object...)} and delegates the call to
	 * {@link #compareAny(Object, List)}
	 * 
	 * @param <T>
	 * @param aCompareValue     Left side value
	 * @param aCompareValueList List of values to be compared with
	 * @return true if any of the compare values matches the leftValue
	 */
	@SafeVarargs
	public static <T> boolean compareAny(T aCompareValue, T... aCompareValueList) {
		return compareAny(aCompareValue, CollectionUtils.toList(aCompareValueList));
	}

	/**
	 * This method checks if the given value compares to any of the values in the
	 * provided list
	 * 
	 * @param <T>
	 * @param aCompareValue     Left side value
	 * @param aCompareValueList List of values to be compared with
	 * @return true if any of the values in the list passes
	 *         {@link #compare(Object, Object)}
	 */
	public static <T> boolean compareAny(T aCompareValue, List<T> aCompareValueList) {
		if (isEmpty(aCompareValue) || isEmpty(aCompareValueList)) {
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

		String message = StringUtils.isEmpty(aError.getMessage()) ? aError.getClass().getName() : aError.getMessage();

		LOGGER.trace("message: {}", message);
		return message;
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
		String errorLog = writer.getBuffer().toString();

		LOGGER.trace("errorLog: {}", errorLog);
		return errorLog;
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * hidden constructor
	 */
	private CommonUtils() {
	}
}