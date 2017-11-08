package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author vsudhakar
public class AvatarTest {

    @Test
    public void equals() {
        Avatar avatar = new Avatar();

        // same object -> true
        assertTrue(avatar.equals(avatar));

        // same values -> true
        Avatar avatarCopy = new Avatar();
        assertTrue(avatar.equals(avatarCopy));

        // different types -> false
        assertFalse(avatar.equals(1));

        // null -> false
        assertFalse(avatar.equals(null));

        // different paths -> false
        try {
            Avatar avatarDifferent = new Avatar("./src/test/avatars/generic_avatar.png");
            assertFalse(avatarCopy.equals(avatarDifferent));
        } catch (IllegalValueException e) {
            assert(false);
        }
    }

    @Test
    public void isInvalidImage() {
        String invalidFile = Avatar.getDirectoryPath("nonexistent.fake");
        assertFalse(Avatar.validFile(invalidFile));
    }

    @Test
    public void isValidImage() {
        String validFile = "./src/test/avatars/generic_avatar.png";
        assertTrue(Avatar.validFile(validFile));
    }
}
