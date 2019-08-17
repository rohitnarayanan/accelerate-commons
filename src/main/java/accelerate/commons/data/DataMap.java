package accelerate.commons.data;

import java.util.HashMap;
import java.util.Map;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.exception.ApplicationException;
import accelerate.commons.util.CommonUtils;
import accelerate.commons.util.JacksonUtils;

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

		dataMap.addAll(aArgs);
		return dataMap;
	}

	/**
	 * {@link Map#put(Object, Object)} overload to chain multiple calls
	 * 
	 * @param aKey
	 * @param aValue
	 * @return
	 */
	public DataMap add(String aKey, Object aValue) {
		super.put(aKey, aValue);
		return this;
	}

	/**
	 * {@link Map#putAll(Map)} overload to chain multiple calls
	 * 
	 * @param aSourceMap
	 * @return
	 */
	public DataMap addAll(Map<? extends String, ? extends Object> aSourceMap) {
		super.putAll(aSourceMap);
		return this;
	}

	/**
	 * Shortcut method to allow multiple key/value as VarArgs
	 * 
	 * @param aArgs
	 * @return
	 */
	public DataMap addAll(Object... aArgs) {
		if (CommonUtils.isEmpty(aArgs)) {
			return this;
		}

		if ((aArgs.length % 2) != 0) {
			throw new ApplicationException("Expected key/value pairs. Current number of arguments: {}", aArgs.length);
		}

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
	 * @param <T>
	 * @param aKey
	 * @param aDefaultValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOrDefault(String aKey, T aDefaultValue) {
		return (T) super.getOrDefault(aKey, aDefaultValue);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public String getString(String aKey) {
		return super.getOrDefault(aKey, CommonConstants.EMPTY_STRING).toString();
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @param aClass
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public <T extends Number> T getNumber(String aKey, Class<T> aClass) {
		Object value = get(aKey);
		if (value == null) {
			return null;
		}

		return aClass.cast(value);
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
	 * This methods returns a JSON representation of this map
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toJSON() throws ApplicationException {
		return serialize(0);
	}

	/**
	 * This methods returns a XML representation of this map
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toXML() throws ApplicationException {
		return serialize(1);
	}

	/**
	 * This methods returns a YAML representation of this map
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toYAML() throws ApplicationException {
		return serialize(2);
	}

	/**
	 * This methods returns a serialized representation of this map
	 * 
	 * @param aMode
	 *              <dd>0 or any other input: JSON</dd>
	 *              <dd>1: XML</dd>
	 *              <dd>2: YAML</dd>
	 * @return
	 * @throws ApplicationException
	 */
	private String serialize(int aMode) throws ApplicationException {
		switch (aMode) {
		case 1:
			return JacksonUtils.toXML(this);
		case 2:
			return JacksonUtils.toYAML(this);
		default:
			return JacksonUtils.toJSON(this);
		}
	}
}
