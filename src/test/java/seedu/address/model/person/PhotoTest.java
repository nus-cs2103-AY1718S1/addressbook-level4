package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_PHOTO_URL;

import org.junit.Test;

public class PhotoTest {
    @Test
    public void isValidPhotoUrl() {
        //valid URL
        assertTrue(Photo.isValidPhoto(VALID_LOCAL_PHOTO_URL));
        assertTrue(Photo.isValidPhoto(VALID_WEB_PHOTO_URL));

        //invalid URL
        assertFalse(Photo.isValidPhoto("www.google.com")); //invalid Photo URL
    }
}
