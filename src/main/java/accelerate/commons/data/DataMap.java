package accelerate.commons.data;

import java.util.HashMap;
import java.util.Map;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.exception.ApplicationException;
import accelerate.commons.util.CommonUtils;
import accelerate.commons.util.JSONUtils;

/**
 * {@link HashMap} extension with overloaded methods for easy loading, method
 * chaining and type-casted getters
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class DataMap extends HashMap<String, Object> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * static method to build new instance and allow method chaining
	 * 
	 * @param aArgs
	 * @return
	 */
	public static DataMap newMap(Object... aArgs) {
		DataMap dataMap = new DataMap();

		if (!CommonUtils.isEmpty(aArgs)) {
			dataMap.putAllAnd(aArgs);
		}

		return dataMap;
	}

	/**
	 * hidden constructor
	 */
	private DataMap() {
	}

	/**
	 * {@link Map#put(Object, Object)} overload to chain multiple calls
	 * 
	 * @param aKey
	 * @param aValue
	 * @return
	 */
	public DataMap putAnd(String aKey, Object aValue) {
		put(aKey, aValue);
		return this;
	}

	/**
	 * {@link Map#putAll(Map)} overload to chain multiple calls
	 * 
	 * @param aSourceMap
	 * @return
	 */
	public DataMap putAllAnd(Map<? extends String, ? extends Object> aSourceMap) {
		super.putAll(aSourceMap);
		return this;
	}

	/**
	 * Shortcut method to allow multiple key/value as VarArgs
	 * 
	 * @param aArgs
	 * @return
	 */
	public DataMap putAllAnd(Object... aArgs) {
		for (int idx = 0; idx < aArgs.length; idx += 2) {
			put((String) aArgs[idx], aArgs[idx + 1]);
		}

		return this;
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String aKey) {
		return (T) super.get(aKey);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public String getString(String aKey) {
		return getOrDefault(aKey, CommonConstants.EMPTY_STRING).toString();
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public Integer getInt(String aKey) {
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
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public boolean checkValue(String aKey, Object aValue) {
		return CommonUtils.compare(get(aKey), aValue);
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T remove(String aKey) {
		return (T) super.remove(aKey);
	}

	/**
	 * This methods returns a JSON representation of this Map
	 * 
	 * @return
	 * @throws ApplicationException thrown due to
	 *                              {@link JSONUtils#serialize(Object)}
	 */
	public String toJSON() throws ApplicationException {
		return JSONUtils.serialize(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return
	 * @throws ApplicationException thrown due to {@link #toJSON()}
	 */
	@Override
	public String toString() throws ApplicationException {
		return toJSON();
	}
}
