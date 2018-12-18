package accelerate.commons.exceptions;

import org.slf4j.helpers.MessageFormatter;

/**
 * This is a simple {@link RuntimeException} extension providing wrapper methods
 * to check and throw exceptions.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 20, 2018
 */
public class ApplicationException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 *
	 * @param aCause
	 */
	public ApplicationException(Throwable aCause) {
		super(aCause.getMessage(), aCause);
	}

	/**
	 * Overloaded Constructor
	 *
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public ApplicationException(String aMessage, Object... aMessageArgs) {
		super(MessageFormatter.arrayFormat(aMessage, aMessageArgs).getMessage());
	}

	/**
	 * Overloaded Constructor
	 * 
	 * @param aCause
	 * @param aMessage
	 * @param aMessageArgs
	 */
	public ApplicationException(Throwable aCause, String aMessage, Object... aMessageArgs) {
		super(MessageFormatter.arrayFormat(aMessage, aMessageArgs).getMessage(), aCause);
	}

	/**
	 * @param        <K> Generic return type
	 * @param aError see {@link #checkAndThrow(Exception, String, Object...)}
	 * @return see {@link #checkAndThrow(Exception, String, Object...)}
	 * @throws ApplicationException checks instance throws original or wrapped
	 *                              exception
	 */
	public static <K> K checkAndThrow(Throwable aError) throws ApplicationException {
		throw (aError instanceof ApplicationException) ? (ApplicationException) aError
				: new ApplicationException(aError);
	}

	/**
	 * @param              <K> Generic return type
	 * @param aError
	 * @param aMessage
	 * @param aMessageArgs
	 * @return need a return clause to satisfy compiler checks when this method gets
	 *         calls from within catch block of methods with a return clause
	 * @throws ApplicationException checks instance throws original or wrapped
	 *                              exception
	 */
	public static <K> K checkAndThrow(Throwable aError, String aMessage, Object... aMessageArgs)
			throws ApplicationException {
		throw (aError instanceof ApplicationException) ? (ApplicationException) aError
				: new ApplicationException(aError, aMessage, aMessageArgs);
	}
}
