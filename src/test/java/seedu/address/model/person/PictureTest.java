//@@author Jemereny
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PictureTest {

    private static final String DEFAULT_PICTURE_LOCATION = "src/main/resources/images/";

    @Test
    public void isValidPicture() {
        // invalid pictures
        assertFalse(Picture.isValidPicture("default_profile"));
        assertFalse(Picture.isValidPicture(DEFAULT_PICTURE_LOCATION + "default_profile.jpg"));

        // valid pictures
        assertTrue(Picture.isValidPicture(null));
        assertTrue(Picture.isValidPicture(DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_PICTURE));
    }
}
