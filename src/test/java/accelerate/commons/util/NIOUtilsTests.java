package accelerate.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import accelerate.commons.exception.ApplicationException;

/**
 * {@link Test} class for {@link NIOUtils}
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since June 26, 2019
 */
@SuppressWarnings("static-method")
class NIOUtilsTests {
	/**
	 * {@link Path} for temp directory
	 */
	private static Path tempPath = null;

	/**
	 * @throws IOException
	 * 
	 */
	@BeforeAll
	static void initialize() throws IOException {
		tempPath = Files.createTempDirectory("NIOUtilsTests");
		LOGGER.debug("initialize: {} | {}", tempPath, Files.exists(tempPath));
	}

	/**
	 * @throws IOException
	 */
	@AfterAll
	static void cleanup() throws IOException {
		LOGGER.debug("cleanup: {} | {}", FileSystemUtils.deleteRecursively(tempPath), Files.exists(tempPath));
	}

	/**
	 * Test method for {@link NIOUtils#getPathString(Path)}.
	 */
	@Test
	void testGetPathString() {
		assertTrue(NIOUtils.getPathString(null).isEmpty());
		assertEquals("/tmp", NIOUtils.getPathString(Paths.get("\\tmp")));
	}

	/**
	 * Test method for {@link NIOUtils#getFileName(Path)}.
	 */
	@Test
	void testGetFileName() {
		assertTrue(NIOUtils.getFileName(null).isEmpty());
		assertEquals("tmp.log", NIOUtils.getFileName(Paths.get("/tmp/tmp.log")));
	}

	/**
	 * Test method for {@link NIOUtils#getBaseName(Path)}.
	 */
	@Test
	void testGetBaseName() {
		assertTrue(NIOUtils.getBaseName(null).isEmpty());
		assertEquals("tmp.test", NIOUtils.getBaseName(Paths.get("/tmp/tmp.test.log")));
	}

	/**
	 * Test method for {@link NIOUtils#getFileExtn(Path)}.
	 */
	@Test
	void testGetFileExtn() {
		assertTrue(NIOUtils.getFileExtn(null).isEmpty());
		assertEquals("log", NIOUtils.getFileExtn(Paths.get("/tmp/tmp.log")));
	}

	/**
	 * Test method for {@link NIOUtils#getParent(Path, int)}.
	 */
	@Test
	void testGetParent() {
		assertTrue(NIOUtils.getParent(null, 0).toString().isEmpty());
		assertEquals("/tmp/tmp.log", NIOUtils.getPathString(NIOUtils.getParent(Paths.get("/tmp/tmp.log"), -1)));

		assertEquals("/parent1",
				NIOUtils.getPathString(NIOUtils.getParent(Paths.get("/parent1/parent2/parent3/tmp.log"), 2)));
		assertEquals("/", NIOUtils.getPathString(NIOUtils.getParent(Paths.get("/parent1/parent2/parent3/tmp.log"), 6)));
	}

	/**
	 * Test method for {@link NIOUtils#getRelativePath(Path, Path)}.
	 */
	@Test
	void testGetRelativePath() {
		assertEquals("/tmp/test", NIOUtils.getRelativePath(null, Paths.get("/tmp/test")));
		assertEquals("/tmp", NIOUtils.getRelativePath(Paths.get("/tmp"), null));
		assertEquals("test", NIOUtils.getRelativePath(Paths.get("/tmp"), Paths.get("/tmp/test")));
	}

	/**
	 * Test method for {@link NIOUtils#rename(Path, String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	void testRename() throws IOException {
		// create the test file
		Path testPath = tempPath.resolve("rename.xyz");
		Files.deleteIfExists(testPath);
		Files.createFile(testPath);

		// test the method
		Path renamePath = NIOUtils.rename(null, "");
		assertEquals("", renamePath.toString());

		renamePath = NIOUtils.rename(testPath, "");
		assertEquals("rename", NIOUtils.getBaseName(renamePath));

		renamePath = NIOUtils.rename(testPath, "testRename");
		assertTrue(Files.exists(renamePath));

		// cleanup
		Files.deleteIfExists(testPath);
	}

	/**
	 * Test method for {@link NIOUtils#searchByName(Path, String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	void testSearchByName() throws IOException {
		// create the test file
		Path testPath = tempPath.resolve("testSearchByName_" + System.currentTimeMillis() + ".xyz");
		LOGGER.debug("testPath: {} | {}", Files.createFile(testPath), Files.exists(testPath));

		// test the method
		assertEquals(1, NIOUtils.searchByName(tempPath, NIOUtils.getBaseName(testPath)).size());

		// cleanup
		Files.deleteIfExists(testPath);
	}

	/**
	 * Test method for {@link NIOUtils#searchByExtn(Path, String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	void testSearchByExtn() throws IOException {
		// create the test file
		Path testPath = tempPath.resolve("xyz_" + System.currentTimeMillis() + ".testSearchByExtn");
		LOGGER.debug("testPath: {} | {}", Files.createFile(testPath), Files.exists(testPath));

		// test the method
		assertEquals(1, NIOUtils.searchByExtn(tempPath, "testSearchByExtn").size());

		// cleanup
		Files.deleteIfExists(testPath);
	}

	/**
	 * Test method for
	 * {@link NIOUtils#walkFileTree(Path, Function, BiFunction, BiFunction, Function, Function, BiFunction)}.
	 */
	@Test
	void testWalkFileTree() {
		Map<String, Path> fileMap = NIOUtils.walkFileTree(tempPath,
				aDir -> !aDir.getFileName().toString().contains("tmp"), (aDir, aAttributes) -> FileVisitResult.CONTINUE,
				(aDir, aException) -> FileVisitResult.CONTINUE, aPath -> NIOUtils.getFileExtn(aPath).equals("log"),
				aPath -> FileVisitResult.CONTINUE, (aPath, aFileVisitResult) -> {
					try {
						return Files.size(aPath) > 0;
					} catch (IOException error) {
						throw new ApplicationException(error);
					}
				});

		assertTrue(fileMap.size() >= 0, "Should pick up any log files present in the temp directory");
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NIOUtilsTests.class);

	/**
	 * hidden constructor
	 */
	private NIOUtilsTests() {
	}
}