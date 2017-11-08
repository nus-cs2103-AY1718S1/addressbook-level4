//@@author PhuaJunJie
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GoogleIdTest {

    @Test
    public void isValidId() {
        // invalid GoogleId
        assertFalse(GoogleId.isValidGoogleId("")); // empty string
        assertFalse(GoogleId.isValidGoogleId(" ")); // spaces only


        // valid GoogleId
        assertTrue(GoogleId.isValidGoogleId("1234567890")); // numbers
        assertTrue(GoogleId.isValidGoogleId("not GoogleContact")); // Letters
        assertTrue(GoogleId.isValidGoogleId("f2134324")); // alphanumeric

    }
}
