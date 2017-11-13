package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_URL_LARGE_IMAGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_URL_MISSING_FILETYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_URL_MISSING_HTTP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;

import org.junit.Test;

//@@author karrui
public class AvatarTest {
    @Test
    public void isValidAvatar() {
        // invalid avatars
        assertFalse(Avatar.isValidPath("abcde")); // not an image path
        assertFalse(Avatar.isValidPath(" ")); // spaces only
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_MISSING_HTTP)); //no "http://" header
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_MISSING_FILETYPE)); // no filetype trailer
        assertFalse(Avatar.isValidPath("https://hopefullydoesnotexist.com/doesnotexist.png")); // image does not exist
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_LARGE_IMAGE)); // image is too large

        // valid avatars
        assertTrue(Avatar.isValidPath(VALID_AVATAR_IMAGE_URL_ONE)); // valid URL
        assertTrue(Avatar.isValidPath(VALID_AVATAR_FILEPATH));  // valid filepath
        assertTrue(Avatar.isValidPath("")); //nothing, default avatar
    }
}
