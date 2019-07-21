package accelerate.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.exception.ApplicationException;

/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since July 20, 2019
 */
public class StreamUtils {
	/**
	 * @param <T>
	 * @param aSourcePath
	 * @param aStreamProcessor
	 * @return
	 */
	public static <T> T loadInputStream(String aSourcePath, Function<InputStream, T> aStreamProcessor) {
		String sourcePath = StringUtils.trim(aSourcePath);
		URL resourceURL = null;
		if (sourcePath.startsWith(CommonConstants.UNIX_PATH_SEPARATOR)) {
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
}
