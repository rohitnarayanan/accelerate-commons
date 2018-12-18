package accelerate.commons.exceptions;

/**
 * This is an extension of {@link ApplicationException} class. Its main purpose
 * is to allow developers to skip code blocks and manage code flow instead of
 * writing verbose conditional statements.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public class FlowControlException extends ApplicationException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 * 
	 * @param aMessage
	 */
	public FlowControlException(String aMessage) {
		super(aMessage);
	}
}