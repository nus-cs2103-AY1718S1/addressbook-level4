package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
public class FaceBookUserNameTest {

    @Test
    public void isValidUsername() throws IllegalValueException {
        // invalid addresses
        FacebookUsername username = new FacebookUsername("ronak.lakhotia");
        FacebookUsername usernameCopy = new FacebookUsername("ronak.lakhotia");
        assertFalse(username.equals(usernameCopy));
        assertTrue(username.equals(username));
        assertFalse(username.equals(null));
        assertFalse(username.equals(5));
    }
}

