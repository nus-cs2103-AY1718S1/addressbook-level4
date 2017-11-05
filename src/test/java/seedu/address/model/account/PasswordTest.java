//@@author cqhchan
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordTest {

    @Test
    public void isValidPassword() {
        // invalid addresses
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only

        // valid addresses
        assertTrue(Password.isValidPassword("Passdwner"));
        assertTrue(Password.isValidPassword("11231784.;lkfw")); // one character
        assertTrue(Password.isValidPassword("Francisco CA 2349879; USA")); // long address
    }
}
