//@@author tshradheya
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddressBookPictureStorageTest {

    private static final String PATH = "/pictures/default.png";
    private static final String INVALID_PATH = ".2? 2./";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTest() {
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(PATH);
        assertEquals(addressBookPictureStorage.getAddressBookPicturePath(), PATH);
    }

    @Test
    public void saveAddressBookPicturePath_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(null);
        addressBookPictureStorage.createPictureStorageFolder();
    }
}
