package accelerate.commons.exception;

import java.util.Map;

import org.slf4j.helpers.MessageFormatter;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.data.DataMap;
import accelerate.commons.util.CommonUtils;
import accelerate.commons.util.JacksonUtils;

/**
 * This is a simple {@link RuntimeException} extension providing wrapper methods
 * to check and throw exceptions.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class ApplicationException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * custom data to be attached to the exception
	 */
	private transient DataMap dataMap = DataMap.newMap();

	/**
	 * Delegate constructor for {@link RuntimeException#RuntimeException()}
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * Delegate constructor for {@link RuntimeException#RuntimeException(String)}
	 * 
	 * @param aMessage
	 */
	public ApplicationException(String aMessage) {
		super(aMessage);
	}

	/**
	 * Delegate constructor for
	 * {@link RuntimeException#RuntimeException(String, Throwable)}
	 * 
	 * @param aMessage
	 * @param aCause
	 */
	public ApplicationException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Delegate constructor for {@link RuntimeException#RuntimeException(Throwable)}
	 *
	 * @param aCause
	 */
	public ApplicationException(Throwable aCause) {
		super(aCause);
	}

	/**
	 * Delegate constructor for
	 * {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}
	 * 
	 * @param aMessage
	 * @param aCause
	 * @param aEnableSuppression
	 * @param aWritableStackTrace
	 */
	public ApplicationException(String aMessage, Throwable aCause, boolean aEnableSuppression,
			boolean aWritableStackTrace) {
		super(aMessage, aCause, aEnableSuppression, aWritableStackTrace);
	}

	/**
	 * Overloaded constructor fpr {@link #ApplicationException(String)}
	 *
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public ApplicationException(String aMessage, Object... aMessageArgs) {
		this(MessageFormatter.arrayFormat(aMessage, aMessageArgs).getMessage());
	}

	/**
	 * Overloaded constructor fpr {@link #ApplicationException(String, Throwable)}
	 * 
	 * @param aCause
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public ApplicationException(Throwable aCause, String aMessage, Object... aMessageArgs) {
		super(MessageFormatter.arrayFormat(aMessage, aMessageArgs).getMessage(), aCause);
	}

	/**
	 * @param aError see {@link #checkAndThrow(Throwable, String, Object...)}
	 * @return see {@link #checkAndThrow(Throwable, String, Object...)}
	 * @throws ApplicationException checks instance throws original or wrapped
	 *                              exception
	 */
	public static ApplicationException checkAndThrow(Throwable aError) throws ApplicationException {
		throw (aError instanceof ApplicationException) ? (ApplicationException) aError
				: new ApplicationException(aError);
	}

	/**
	 * @param aError
	 * @param aMessage
	 * @param aMessageArgs
	 * @return need a return clause to satisfy compiler checks when this method gets
	 *         calls from within catch block of methods with a return clause
	 * @throws ApplicationException checks instance throws original or wrapped
	 *                              exception
	 */
	public static ApplicationException checkAndThrow(Throwable aError, String aMessage, Object... aMessageArgs)
			throws ApplicationException {
		throw (aError instanceof ApplicationException) ? (ApplicationException) aError
				: new ApplicationException(aError, aMessage, aMessageArgs);
	}

	/**
	 * Getter method for "dataMap" property
	 * 
	 * @return dataMap
	 */
	public DataMap getDataMap() {
		return this.dataMap;
	}

	/**
	 * This methods returns a JSON representation of this exception
	 * 
	 * @return
	 * @throws ApplicationException thrown due to
	 *                              {@link JacksonUtils#buildJSON(Object...)}
	 */
	public String toJSON() throws ApplicationException {
		return serialize(0);
	}

	/**
	 * This methods returns a XML representation of this exception
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toXML() throws ApplicationException {
		return serialize(1);
	}

	/**
	 * This methods returns a YAML representation of this exception
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toYAML() throws ApplicationException {
		return serialize(2);
	}

	/**
	 * This methods returns a serialized representation of this exception
	 * 
	 * @param aMode
	 *              <dd>0 or any other input: JSON</dd>
	 *              <dd>1: XML</dd>
	 *              <dd>2: YAML</dd>
	 * @return
	 * @throws ApplicationException
	 */
	private String serialize(int aMode) throws ApplicationException {
		Object[] elements = new Object[] { "message", getMessage(), "stacktrace", CommonUtils.getErrorLog(this), "data",
				getDataMap() };

		switch (aMode) {
		case 1:
			return JacksonUtils.buildXML("ApplicationException", elements);
		case 2:
			return JacksonUtils.buildYAML(elements);
		default:
			return JacksonUtils.buildJSON(elements);
		}
	}

	/*
	 * Delegate Methods
	 */
	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see accelerate.commons.data.DataMap#add(java.lang.String, java.lang.Object)
	 */
	public ApplicationException add(String aKey, Object aValue) {
		this.dataMap.add(aKey, aValue);
		return this;
	}

	/**
	 * @param aSourceMap
	 * @return
	 * @see accelerate.commons.data.DataMap#addAll(java.util.Map)
	 */
	public ApplicationException addAll(Map<? extends String, ? extends Object> aSourceMap) {
		this.dataMap.addAll(aSourceMap);
		return this;
	}

	/**
	 * @param aArgs
	 * @return
	 * @see accelerate.commons.data.DataMap#addAll(Object...)
	 */
	public ApplicationException addAll(Object... aArgs) {
		this.dataMap.addAll(aArgs);
		return this;
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public <T> T get(String aKey) {
		return this.dataMap.get(aKey);
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @param aDefaultValue
	 * @return
	 */
	public <T> T getOrDefault(String aKey, T aDefaultValue) {
		return this.dataMap.getOrDefault(aKey, aDefaultValue);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public String getString(String aKey) {
		Object value = this.dataMap.getOrDefault(aKey, CommonConstants.EMPTY_STRING);
		return value.toString();
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @param aClass
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public <T extends Number> T getNumber(String aKey, Class<T> aClass) {
		return this.dataMap.getNumber(aKey, aClass);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public boolean checkValue(String aKey, Object aValue) {
		return this.dataMap.checkValue(aKey, aValue);
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 */
	public <T> T remove(String aKey) {
		return this.dataMap.remove(aKey);
	}
}