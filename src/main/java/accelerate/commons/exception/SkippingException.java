package accelerate.commons.exception;

/**
 * This is an extension of {@link ApplicationException} class. Its main purpose
 * is to allow developers to skip code blocks and manage code flow instead of
 * writing verbose conditional statements.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class SkippingException extends ApplicationException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 * 
	 * @param aMessage
	 */
	public SkippingException(String aMessage) {
		super(aMessage);
	}
}