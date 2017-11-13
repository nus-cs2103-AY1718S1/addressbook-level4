package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL;

import org.junit.Test;

//@@author soonwj
public class ProfilePicTest {
    @Test
    public void isValidUrl() {
        // invalid URL
        assertFalse(ProfilePic.isValidUrl("")); // empty string
        assertFalse(ProfilePic.isValidUrl("images/fail.png")); // malformed URL

        // valid addresses
        assertTrue(ProfilePic.isValidUrl(VALID_WEB_IMAGE_URL)); //web URL
        assertTrue(ProfilePic.isValidUrl(VALID_LOCAL_IMAGE_URL)); //local URL
    }
}
