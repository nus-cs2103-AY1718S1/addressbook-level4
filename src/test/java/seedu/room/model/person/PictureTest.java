package seedu.room.model.person;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

//@@author shitian007
public class PictureTest {

    @Test
    public void isValidUrl() {
        // Invalid picture urls
        assertFalse(Picture.isValidImageUrl("")); // empty string
        assertFalse(Picture.isValidImageUrl(" ")); // spaces only
        assertFalse(Picture.isValidImageUrl("folder//folder/image.jpg")); // Double slash invalid file url

        // Valid picture numbers
        assertTrue(Picture.isValidImageUrl("folder1/folder2/image.jpg"));
        assertTrue(Picture.isValidImageUrl("folder1/folder2/image.png"));

        // Default picture url
        Picture defaultPicture = new Picture();
        assertTrue(defaultPicture.getPictureUrl().equals(Picture.PLACEHOLDER_IMAGE));
    }
}
