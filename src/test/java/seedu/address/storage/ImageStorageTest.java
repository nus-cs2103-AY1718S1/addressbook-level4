package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ImageStorageTest {

    private static String PATH = "images/default.png";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTest() {
        ImageStorage addressBookImageStorage = new ImageStorage(PATH);
        assertEquals(addressBookImageStorage.getAddressBookImagePath(), PATH);
    }

    @Test
    public void saveAddressBookImagePath_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        ImageStorage addressBookImageStorage = new ImageStorage(null);
        addressBookImageStorage.createImageStorageFolder();
    }
}
