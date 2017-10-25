package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void isValidPhotoURL() {
        // invalid photoURL
        assertFalse(Photo.isValidPhotoURL("")); // empty string
        assertFalse(Photo.isValidPhotoURL(" ")); // spaces only
        assertFalse(Photo.isValidPhotoURL("image/photo")); // no file extension
        assertFalse(Photo.isValidPhotoURL(".jpg")); // no file name
        assertFalse(Photo.isValidPhotoURL("photo.")); // empty extension
        assertFalse(Photo.isValidPhotoURL("photo.png")); // wrong extension

        // valid photoURL
        assertTrue(Photo.isValidPhotoURL("src/main/resources/images/John.jpg" +
                ".jpg")); // valid long file path
        assertTrue(Photo.isValidPhotoURL("John.jpg")); // valid short file path
    }
}
