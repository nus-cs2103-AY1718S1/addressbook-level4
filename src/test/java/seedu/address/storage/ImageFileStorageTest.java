package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.testutil.TestUtil;

//@@author JunQuann
public class ImageFileStorageTest {
    private static final String TEST_DATA_FOLDER = "./src/test/data/ImageFileStorageTest/";
    private static final String TEST_FILENAME = "test";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private final String testImage = TestUtil.getTestImgPath();

    @Test
    public void createImageFolder_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        createImageDir(null);
    }

    public void createImageDir(String filePath) throws IOException {
        new ImageFileStorage(filePath).createImageDir();
    }

    @Test
    public void assertImageFile() {
        String expectedFile = TEST_DATA_FOLDER + TEST_FILENAME + ".png";
        String actualFile = new ImageFileStorage(TEST_DATA_FOLDER).getImageFilePath(TEST_FILENAME);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    public void copiedImage_success() throws IOException {
        String filePath = testFolder.getRoot().getPath();
        ImageFileStorage imageFileStorage = new ImageFileStorage(filePath);
        imageFileStorage.copyImage(testImage, TEST_FILENAME);
        File expectedFile = new File(testImage);
        File actualFile = new File(imageFileStorage.getImageFilePath(TEST_FILENAME));
        assertFileContentEqual(expectedFile, actualFile);
    }

    /**
     * Asserts that the actual file {@code actualFile} has the same binary data as {@code expectedFile}.
     */
    public void assertFileContentEqual(File expectedFile, File actualFile) throws IOException {
        byte[] expectedContent = Files.readAllBytes(expectedFile.toPath());
        byte[] actualContent = Files.readAllBytes(actualFile.toPath());
        boolean equal = Arrays.equals(expectedContent, actualContent);
        assertTrue(equal);
    }
}
