package accelerate.commons.data;

import static accelerate.commons.constants.CommonConstants.EMPTY_STRING;

import java.util.HashMap;

import accelerate.commons.utils.JSONUtils;

/**
 * {@link HashMap} extension with method chaining, type-casted getters
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @param <V> any value type
 * @since October 20, 2018
 */
public class DataMap<V> extends HashMap<String, V> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param <T>
	 * @return
	 */
	public static <T> DataMap<T> newMap() {
		return new DataMap<>();
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 */
	public DataMap<V> putAnd(String aKey, V aValue) {
		put(aKey, aValue);
		return this;
	}

	/**
	 * @param aKey
	 * @return String value stored
	 */
	public String getString(String aKey) {
		V value = get(aKey);
		if (value == null) {
			return EMPTY_STRING;
		}

		return value.toString();
	}

	/**
	 * @param aKey
	 * @return Integer value stored
	 */
	public Integer getInteger(String aKey) {
		Object value = get(aKey);
		if (value == null) {
			return null;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}

		return Integer.parseInt(value.toString());
	}

	/**
	 * @param aKey
	 * @return Boolean value stored
	 */
	public boolean getBoolean(String aKey) {
		Object value = get(aKey);
		if (value == null) {
			return false;
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		}

		return Boolean.parseBoolean(value.toString());
	}

	/**
	 * This methods returns a JSON representation of this Map
	 * 
	 * @return
	 */
	public String toJSON() {
		return JSONUtils.serialize(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return
	 */
	@Override
	public String toString() {
		return toJSON();
	}
}
