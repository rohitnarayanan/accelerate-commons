package accelerate.commons.util;

import static accelerate.commons.constant.CommonConstants.EMPTY_STRING;
import static accelerate.commons.constant.CommonConstants.PERIOD;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.constant.CommonConstants;
import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for java.nio.file operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class NIOUtils {
	/**
	 * Method to consistently return path in UNIX format with '/' separator instead
	 * of '\\' used in windows.
	 * 
	 * @param aPath
	 * @return Path String in UNIX format
	 */
	public static String getPathString(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String pathString = aPath.toString().replace(CommonConstants.WINDOWS_PATH_SEPARATOR,
				CommonConstants.UNIX_PATH_SEPARATOR);
		LOGGER.trace("getPathString: [{}] [{}]", aPath, pathString);

		return pathString;
	}

	/**
	 * @param aPath
	 * @return File name
	 */
	public static String getFileName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = aPath.getFileName().toString();
		LOGGER.trace("getFileName: [{}] [{}]", aPath, fileName);

		return fileName;
	}

	/**
	 * @param aPath
	 * @return File name without extension
	 */
	public static String getBaseName(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = getFileName(aPath);
		int extnIndex = fileName.lastIndexOf(CommonConstants.PERIOD);
		extnIndex = (extnIndex == -1) ? fileName.length() : extnIndex;
		String baseName = fileName.substring(0, extnIndex);

		LOGGER.trace("getBaseName: [{}] [{}]", aPath, baseName);

		return baseName;
	}

	/**
	 * @param aPath
	 * @return File extension
	 */
	public static String getFileExtn(Path aPath) {
		if (aPath == null) {
			return EMPTY_STRING;
		}

		String fileName = getFileName(aPath);
		int extnIndex = fileName.lastIndexOf(CommonConstants.PERIOD);
		String fileExtn = (extnIndex == -1) ? CommonConstants.EMPTY_STRING : fileName.substring(extnIndex + 1);

		LOGGER.trace("getFileExtn: [{}] [{}]", aPath, fileExtn);

		return fileExtn;
	}

	/**
	 * The method gets the parent {@link Path} up to the given level.
	 * 
	 * @param aPath
	 * @param aUpLevel
	 * @return Parent path up to the given level or the root path
	 */
	public static Path getParent(Path aPath, int aUpLevel) {
		if (aPath == null) {
			return Paths.get(EMPTY_STRING);
		}

		if (aUpLevel <= 0) {
			return aPath;
		}

		Path parent = aPath.getParent();
		for (int idx = 1; idx <= aUpLevel; idx++) {
			if (parent.getParent() == null) {
				return parent;
			}

			parent = parent.getParent();
		}

		LOGGER.trace("getParent: [{}] [{}] [{}]", aPath, aUpLevel, parent);

		return parent;
	}

	/**
	 * @param aRoot
	 * @param aPath
	 * @return Relative path
	 */
	public static String getRelativePath(Path aRoot, Path aPath) {
		if (aRoot == null) {
			return getPathString(aPath);
		}

		if (aPath == null) {
			return getPathString(aRoot);
		}

		String relativePath = getPathString(aRoot.relativize(aPath));
		LOGGER.trace("getRelativePath: [{}] [{}] [{}]", aRoot, aPath, relativePath);

		return relativePath;
	}

	/**
	 * @param aPath
	 * @param aNewName
	 * @return Relative path
	 * @throws IOException
	 */
	public static Path rename(Path aPath, String aNewName) throws IOException {
		if (aPath == null) {
			return Paths.get(EMPTY_STRING);
		}

		if (CommonUtils.isEmpty(aNewName)) {
			return aPath;
		}

		String extn = getFileExtn(aPath);
		Path newPath = Files.move(aPath, aPath.getParent().resolve(aNewName + PERIOD + extn),
				StandardCopyOption.ATOMIC_MOVE);
		LOGGER.trace("getRelativePath: [{}] [{}] [{}]", aPath, aNewName, newPath);

		return newPath;
	}

	/**
	 * @param aRootPath    path to the file or folder of files
	 * @param aNamePattern text to be searched in the filename
	 * @return {@link Map} of {@link Path} that match the search criteria
	 * @throws ApplicationException thrown by
	 *                              {@link #walkFileTree(Path, Function, BiFunction, BiFunction, Function, Function, BiFunction)}
	 */
	public static Map<String, Path> searchByName(Path aRootPath, String aNamePattern) throws ApplicationException {
		return walkFileTree(aRootPath, null, null, null, null, null,
				(aPath, aFileVisitResult) -> getBaseName(aPath).matches(aNamePattern));
	}

	/**
	 * @param aRootPath   path to the file or folder of files
	 * @param aSearchExtn extension of the file
	 * @return {@link Map} of {@link Path} that match the search criteria
	 * @throws ApplicationException thrown by
	 *                              {@link #walkFileTree(Path, Function, BiFunction, BiFunction, Function, Function, BiFunction)}
	 */
	public static Map<String, Path> searchByExtn(Path aRootPath, String aSearchExtn) throws ApplicationException {
		return walkFileTree(aRootPath, null, null, null, null, null,
				(aPath, aFileVisitResult) -> CommonUtils.compare(getFileExtn(aPath), aSearchExtn));
	}

	/**
	 * @param aRootPath
	 * @param aDirectoryFilter
	 * @param aPreVisitDirectory
	 * @param aPostVisitDirectory
	 * @param aFileFilter
	 * @param aVisitFile
	 * @param aSelector
	 * @return {@link Map} containing files picked up by given Selector
	 * @throws ApplicationException {@link IOException} thrown by
	 *                              {@link Files#walkFileTree(Path, FileVisitor)}
	 */
	public static Map<String, Path> walkFileTree(Path aRootPath, final Function<Path, Boolean> aDirectoryFilter,
			final BiFunction<Path, BasicFileAttributes, FileVisitResult> aPreVisitDirectory,
			final BiFunction<Path, IOException, FileVisitResult> aPostVisitDirectory,
			final Function<Path, Boolean> aFileFilter, final Function<Path, FileVisitResult> aVisitFile,
			final BiFunction<Path, FileVisitResult, Boolean> aSelector) throws ApplicationException {
		final Map<String, Path> fileMap = new TreeMap<>();

		try {
			Files.walkFileTree(aRootPath, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path aPath, BasicFileAttributes aAttrs) {
					if ((aDirectoryFilter != null) && !aDirectoryFilter.apply(aPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					try {
						if (Files.isSameFile(aPath, aRootPath)) {
							return FileVisitResult.CONTINUE;
						}
					} catch (IOException error) {
						ApplicationException.checkAndThrow(error);
					}

					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aPreVisitDirectory != null) {
						visitResult = aPreVisitDirectory.apply(aPath, aAttrs);
					}

					if ((aSelector != null) && aSelector.apply(aPath, visitResult)) {
						fileMap.put(getRelativePath(aRootPath, aPath), aPath);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path aPath, IOException aError) {
					if (aPostVisitDirectory != null) {
						return aPostVisitDirectory.apply(aPath, aError);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path aPath, BasicFileAttributes aAttrs) {
					if ((aFileFilter != null) && !aFileFilter.apply(aPath)) {
						return FileVisitResult.CONTINUE;
					}

					FileVisitResult visitResult = FileVisitResult.CONTINUE;
					if (aVisitFile != null) {
						visitResult = aVisitFile.apply(aPath);
					}

					if ((aSelector != null) && aSelector.apply(aPath, visitResult)) {
						fileMap.put(getRelativePath(aRootPath, aPath), aPath);
					}

					return visitResult;
				}

				@Override
				public FileVisitResult visitFileFailed(Path aPath, IOException aError) throws IOException {
					if (aError != null) {
						throw aError;
					}

					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException error) {
			throw new ApplicationException(error);
		}

		LOGGER.trace("walkFileTree: root=[{}], selectCount=[{}]", aRootPath, fileMap.size());
		return fileMap;
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NIOUtils.class);

	/**
	 * hidden constructor
	 */
	private NIOUtils() {
	}
}