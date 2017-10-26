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
        assertFalse(Photo.isValidPhotoUrl("photo.txt")); // wrong extension
        assertFalse(Photo.isValidPhotoUrl("photo.PNG")); // wrong extension
        // (upper case)

        // valid photoUrl
        assertTrue(Photo.isValidPhotoUrl("src/main/resources/images/John"
                + ".jpg")); // valid relative file path (Linux)
        assertTrue(Photo.isValidPhotoUrl("src/main/resources/images/John"
                + ".jpeg")); // valid relative file path (Linux)
        assertTrue(Photo.isValidPhotoUrl("src/main/resources/images/John"
                + ".png")); // valid relative file path (Linux)
        assertTrue(Photo.isValidPhotoUrl("/~/src/main/resources/images/John"
                + ".jpg")); // valid absolute file path (Linux)
        assertTrue(Photo.isValidPhotoUrl("src\\main\\image.jpg")); // valid
        // relative file path (Windows)
        assertTrue(Photo.isValidPhotoUrl("C:\\src\\main\\image.jpg")); //
        // valid absolute file path (Windows)
        assertTrue(Photo.isValidPhotoUrl("John.jpg")); // valid short file path
    }
}
