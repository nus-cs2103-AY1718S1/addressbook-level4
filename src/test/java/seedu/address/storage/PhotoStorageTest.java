//@@author wishingmaid
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.PhotoStorage.WRITE_FAILURE_MESSAGE;

import java.io.IOException;

import org.junit.Test;

public class PhotoStorageTest {
    @Test
    public void correctHash() {
        String userFilePath = "C:/Users/pigir/Desktop/saltbae.jpg";
        String expectedUniqueFileName = "-694293159";
        Integer userFilePathHashed = userFilePath.hashCode();
        assertEquals(userFilePathHashed.toString(), expectedUniqueFileName);
    }
    @Test
    public void writeSuccess() {
        String imageSource = "src/test/resources/images/noPhoto.png";
    }
    @Test
    public void writeFailure() {
        String userFilePath = "C:/Users/pigir/Desktop/addPhoto.txt";
        int userFilePathHashed = userFilePath.hashCode();
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath, userFilePathHashed);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
}

    