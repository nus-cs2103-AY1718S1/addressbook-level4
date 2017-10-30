package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

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
        String s = File.separator;
        assertTrue(Photo.isValidPhotoUrl("src" + s + "main" + s + "resources" + s + "images" + s + "John"
                + ".jpg")); // valid relative file path
        assertTrue(Photo.isValidPhotoUrl("src" + s + "main" + s + "resources" + s + "images" + s + "John"
                + ".jpeg")); // valid relative file path
        assertTrue(Photo.isValidPhotoUrl("src" + s + "main" + s + "resources" + s + "images" + s + "John"
                + ".png")); // valid relative file path
        assertTrue(Photo.isValidPhotoUrl(s + "Users" + s + "shuangyang" + s + "src" + s + "main" + s
                + "resources" + s + "images" + s + "John.jpg")); // valid absolute file path
        assertTrue(Photo.isValidPhotoUrl("John.jpg")); // valid short file path
    }
}
