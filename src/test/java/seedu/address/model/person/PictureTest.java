package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PictureTest {

    @Test
    public void isValidPicture() {
        // invalid pictures
        assertFalse(Picture.isValidPicture("default_profile"));
        assertFalse(Picture.isValidPicture(Picture.DEFAULT_PICTURE_LOCATION + "default_profile.jpg"));

        // valid pictures
        assertTrue(Picture.isValidPicture(null));
        assertTrue(Picture.isValidPicture(Picture.DEFAULT_PICTURE_LOCATION + "default_profile.png"));
    }
}
