package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void isValidPhotoUrl() {
        // invalid photoUrl
        assertFalse(Photo.isValidPhotoUrl("")); // empty string
        assertFalse(Photo.isValidPhotoUrl(" ")); // spaces only
        assertFalse(Photo.isValidPhotoUrl("image/photo")); // no file extension
        assertFalse(Photo.isValidPhotoUrl(".jpg")); // no file name
        assertFalse(Photo.isValidPhotoUrl("photo.")); // empty extension
        assertFalse(Photo.isValidPhotoUrl("photo.png")); // wrong extension

        // valid photoUrl
        assertTrue(Photo.isValidPhotoUrl("src/main/resources/images/John.jpg"
                + ".jpg")); // valid long file path
        assertTrue(Photo.isValidPhotoUrl("John.jpg")); // valid short file path
    }
}
