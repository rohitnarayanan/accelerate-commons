package accelerate.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.exceptions.ApplicationException;

/**
 * Class providing common utility methods
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class RuntimeUtils {
	/**
	 * {@link Logger} instance
	 */
	private static final Logger _LOGGER = LoggerFactory.getLogger(RuntimeUtils.class);

	/**
	 * hidden constructor
	 */
	private RuntimeUtils() {
	}

	/**
	 * @param aCommand
	 * @param aEnvSettings
	 * @param aExecuteDir
	 * @return Command Output
	 */
	public static String executeOSCommand(String aCommand, String[] aEnvSettings, File aExecuteDir) {
		_LOGGER.debug("OSCommand [{}]", aCommand);

		String outputLine = null;
		StringBuilder outputBuffer = new StringBuilder();

		try {
			Process process = Runtime.getRuntime().exec(aCommand, aEnvSettings,
					(aExecuteDir != null) ? aExecuteDir : new File(System.getProperty("java.io.tmpdir")));

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
				while ((outputLine = reader.readLine()) != null) {
					outputBuffer.append(outputLine);
				}
			}

			_LOGGER.debug("OSCommand Output =>\n{}", outputBuffer);
		} catch (IOException error) {
			throw new ApplicationException(error);
		}

		return outputBuffer.toString();
	}
}