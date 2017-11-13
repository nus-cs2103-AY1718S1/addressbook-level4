//@@author tshradheya
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.parser.exceptions.ImageException;

public class ImageDisplayPictureStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImageDisplayPicture/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_notValidPath_exceptionThrown() throws Exception {

        thrown.expect(ImageException.class);
        DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
        displayPictureStorage.readImageFromDevice("34?/32 2dsd k", 232);
        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    /**
     * Ensures no exception is thrown and command happens successfully
     * @throws IOException not expected
     */
    @Test
    public void read_validPath_success() throws IOException {
        Exception exception = null;
        try {
            DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
            displayPictureStorage.readImageFromDevice(TEST_DATA_FOLDER + "1137944384.png", 1137944384);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(exception, null);
    }

}
