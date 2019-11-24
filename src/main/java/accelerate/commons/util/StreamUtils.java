package accelerate.commons.util;

import static accelerate.commons.constant.CommonConstants.UNIX_PATH_SEPARATOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.exception.ApplicationException;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class StreamUtils {
	/**
	 * @param <T>
	 * @param aSourcePath
	 * @param aStreamProcessor
	 * @return
	 */
	public static <T> T loadInputStream(String aSourcePath, Function<InputStream, T> aStreamProcessor) {
		LOGGER.debug("Loading stream from [{}]", aSourcePath);

		String sourcePath = StringUtils.trim(aSourcePath);
		URL resourceURL = null;
		if (sourcePath.startsWith(UNIX_PATH_SEPARATOR)) {
			resourceURL = ConfigurationUtils.class.getResource(sourcePath);
		} else if (sourcePath.startsWith("classpath:")) {
			resourceURL = ConfigurationUtils.class.getResource(sourcePath.substring(10));
		} else {
			try {
				resourceURL = new URL(sourcePath);
			} catch (MalformedURLException error) {
				throw new ApplicationException(error);
			}
		}

		if (resourceURL == null) {
			throw new ApplicationException("Classpath resource not found: {}", aSourcePath);
		}

		try (InputStream inputStream = resourceURL.openStream()) {
			return aStreamProcessor.apply(inputStream);
		} catch (IOException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aSourcePath
	 * @return
	 */
	public static String readInputStream(String aSourcePath) {
		LOGGER.debug("Reading stream from [{}]", aSourcePath);

		return loadInputStream(aSourcePath, aInputStream -> {
			StringBuilder buffer = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(aInputStream))) {
				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line).append("\n");
				}
			} catch (IOException error) {
				error.printStackTrace();
			}

			return buffer.toString();
		});
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

	/**
	 * hidden constructor
	 */
	private StreamUtils() {
	}
}