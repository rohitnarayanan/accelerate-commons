package accelerate.commons.constant;

/**
 * Class containing basic set of constants that are usually used in app
 * development
 * 
 * @since July 16, 2019
 */
@SuppressWarnings("javadoc")
public final class CommonConstants {
	// system constants
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String UNIX_PATH_SEPARATOR = "/";
	public static final String WINDOWS_PATH_SEPARATOR = "\\";

	// value constants
	public static final String ENV = "env";
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String ENABLED = "enabled";
	public static final String DISABLED = "disabled";

	// brackets
	public static final String BRACKET_OPEN = "(";
	public static final String BRACKET_CLOSE = ")";
	public static final String SQ_BRACKET_OPEN = "[";
	public static final String SQ_BRACKET_CLOSE = "]";
	public static final String BRACE_OPEN = "{";
	public static final String BRACE_CLOSE = "}";

	// delimiters
	public static final String PERIOD = ".";
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String PIPE = "|";
	public static final String POUND = "#";

	public static final String EMPTY_STRING = "";
	public static final String NULL_STRING = "null";
	public static final String ASTERIX = "*";
	public static final String SPACE = " ";
	public static final String SINGLE_QUOTE = "'";
	public static final String DOUBLE_QUOTES = "\"";
	public static final String HYPHEN = "-";
	public static final String UNDERSCORE = "_";
	public static final String EQUALS = "=";
	public static final String PERCENT = "%";
	public static final String QUESTION = "?";

	/**
	 * hidden constructor
	 */
	private CommonConstants() {
	}
}