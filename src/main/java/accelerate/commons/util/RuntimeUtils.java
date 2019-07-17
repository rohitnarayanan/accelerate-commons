package accelerate.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for underlying Runtime
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class RuntimeUtils {
	/**
	 * @param aCommand
	 * @param aEnvSettings
	 * @param aExecuteDir
	 * @return Command Output
	 */
	public static String executeOSCommand(String aCommand, String[] aEnvSettings, File aExecuteDir) {
		LOGGER.debug("OSCommand [{}]", aCommand);

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

			LOGGER.debug("OSCommand Output =>\n{}", outputBuffer);
		} catch (IOException error) {
			throw new ApplicationException(error);
		}

		return outputBuffer.toString();
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeUtils.class);

	/**
	 * hidden constructor
	 */
	private RuntimeUtils() {
	}
}