package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author LuLechuan
public class PhotoTest {

    @Test
    public void isKnownPhoto() {
        // valid photo path
        assertTrue(!Photo.isUnknownPath(System.getProperty("user.dir")
                + "/docs/images/default_photo.png")); // existed path
    }

    @Test
    public void isUnknownPhoto() {
        // invalid photo path
        assertFalse(!Photo.isUnknownPath("")); // empty string
        assertFalse(!Photo.isUnknownPath("doesNotExist.jpg")); // path does not exist
    }
}
