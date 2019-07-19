package accelerate.commons.exception;

import org.slf4j.helpers.MessageFormatter;

import accelerate.commons.data.DataMap;
import accelerate.commons.util.CommonUtils;
import accelerate.commons.util.JSONUtils;

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
	private DataMap dataMap = DataMap.newMap();

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
		super(aCause.getMessage(), aCause);
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
	 * @param aError see {@link #checkAndThrow(Exception, String, Object...)}
	 * @return see {@link #checkAndThrow(Exception, String, Object...)}
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
	 * @param <T>
	 * @param aKey
	 * @return
	 * @see accelerate.commons.data.DataMap#get(java.lang.String)
	 */
	public <T> T get(String aKey) {
		return this.dataMap.get(aKey);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see accelerate.commons.data.DataMap#putAnd(java.lang.String,
	 *      java.lang.Object)
	 */
	public ApplicationException putAnd(String aKey, Object aValue) {
		this.dataMap.putAnd(aKey, aValue);
		return this;
	}

	/**
	 * @param aArgs
	 * @return
	 * @see accelerate.commons.data.DataMap#putAllAnd(Object...)
	 */
	public ApplicationException putAllAnd(Object... aArgs) {
		for (int idx = 0; idx < aArgs.length; idx += 2) {
			this.dataMap.put((String) aArgs[idx], aArgs[idx + 1]);
		}

		return this;
	}

	/**
	 * This methods returns a JSON representation of this exception
	 * 
	 * @return
	 * @throws ApplicationException thrown due to
	 *                              {@link JSONUtils#serialize(Object)}
	 */
	public String toJSON() throws ApplicationException {
		return JSONUtils.buildJSON("message", getMessage(), "stacktrace", CommonUtils.getErrorLog(this), "data",
				getDataMap());
	}
}