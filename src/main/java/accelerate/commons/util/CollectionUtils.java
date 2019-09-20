package accelerate.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.data.DataMap;
import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for collection operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class CollectionUtils {
	/**
	 * Method converts a {@link Properties} instance to {@link Map} for
	 * non-synchronized traversal. NOTE: In case the properties instance is null a
	 * immutable empty map is return for fail-safe loops.
	 * 
	 * @param aProperties - Properties Object
	 * @param type        - Class to be used for converting value
	 * @param <T>         - Type of value
	 * @return Map Object
	 */
	public static <T> Map<String, T> convertToMap(Properties aProperties, Class<T> type) {
		LOGGER.trace("convertToMap [{}]", aProperties);

		Map<String, T> propMap = new HashMap<>();

		/*
		 * populate entries
		 */
		if (!CommonUtils.isEmpty(aProperties)) {
			aProperties.forEach((aKey, aValue) -> propMap.put(aKey.toString(), type.cast(aValue)));
		}

		return propMap;
	}

	/**
	 * Utility method to compare 2 {@link Map} instance and provide details on the
	 * differences
	 * 
	 * @param mapInstanceA
	 * @param mapInstanceB
	 * @return result string
	 */
	public static DataMap compareMaps(Map<String, Object> mapInstanceA, Map<String, Object> mapInstanceB) {
		Object extraA = mapInstanceA.entrySet().stream().filter(entry -> mapInstanceB.get(entry.getKey()) == null)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

		Object extraB = mapInstanceB.entrySet().stream().filter(entry -> mapInstanceA.get(entry.getKey()) == null)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

		Object conflict = mapInstanceA.entrySet().stream().filter(entry -> mapInstanceB.get(entry.getKey()) != null)
				.map(entry -> new Object[] { entry.getKey(), entry.getValue(), mapInstanceB.get(entry.getKey()) })
				.collect(Collectors.toMap(values -> values[0], values -> new Object[] { values[1], values[2] }));

		LOGGER.trace("extraA [{}] | extraB [{}] | conflict [{}]", extraA, extraB, conflict);

		return DataMap.newMap("extraA", extraA, "extraB", extraB, "conflict", conflict);
	}

	/**
	 * @param <E>
	 * @param aCollection
	 * @return array of elements
	 */
	@SafeVarargs
	public final static <E> List<E> toList(E... aCollection) {
		if ((aCollection == null) || (aCollection.length == 0)) {
			return new ArrayList<>();
		}

		return Arrays.asList(aCollection);
	}

//	/**
//	 * @param <E>
//	 * @param aCollection
//	 * @return array of elements
//	 */
//	@SuppressWarnings("unchecked")
//	public static <E> E[] toArray(Collection<? extends E> aCollection) {
//		if (aCollection == null || aCollection.isEmpty()) {
//			return null;
//		}
//
//		return (E[]) aCollection.stream().toArray();
//	}

	/**
	 * Shortcut method to extract a sublist from the middle.
	 *
	 * @param <E>
	 * @param aList
	 * @param aStartIndex position to start from, negative means count back from the
	 *                    end
	 * @param aEndIndex   position to end at (exclusive), negative means count back
	 *                    from the end
	 * @return extracted sub list
	 * @throws ApplicationException on invalid arguments
	 */
	public static <E> List<E> subList(List<E> aList, int aStartIndex, int aEndIndex) throws ApplicationException {
		if (CommonUtils.isEmpty(aList)) {
			return new ArrayList<>();
		}

		int length = aList.size();
		int start = CommonUtils.getAdjustedIndex(aStartIndex, length);
		int end = CommonUtils.getAdjustedIndex(aEndIndex, length);

		// return empty list
		if (start >= end) {
			return new ArrayList<>();
		}

		return aList.subList(start, end);
	}

//	/**
//	 * Shortcut method to extract a sub-array from the middle.
//	 *
//	 * @param <E>
//	 * @param aArray
//	 * @param aStartIndex
//	 * @param aFromEndIndex
//	 * @return extracted sub array
//	 * @throws ApplicationException on invalid arguments
//	 */
//	public static <E> E[] subArray(E[] aArray, int aStartIndex, int aFromEndIndex) throws ApplicationException {
//		if (ObjectUtils.isEmpty(aArray)) {
//			return aArray;
//		}
//
//		int length = aArray.length;
//		int start = CommonUtils.getAdjustedIndex(aStartIndex, length);
//		int end = CommonUtils.getAdjustedIndex(aFromEndIndex, length);
//
//		return ArrayUtils.subarray(aArray, start, end);
//	}

	/**
	 * @param <E>
	 * @param aList
	 * @return true, if Collection was sorted
	 */
	public static <E extends Comparable<E>> boolean sort(List<E> aList) {
		if ((aList == null) || aList.isEmpty()) {
			return false;
		}

		Collections.sort(aList, (aElement1, aElement2) -> aElement1.compareTo(aElement2));
		return true;
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionUtils.class);

	/**
	 * hidden constructor
	 */
	private CollectionUtils() {
	}
}