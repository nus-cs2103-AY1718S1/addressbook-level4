//@@author sunarjo-denny
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {
    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("01/01/1995")); // DD/MM/YYYY format
        assertFalse(Birthday.isValidBirthday("01-01-1995")); // DD-MM-YYYY format
        assertFalse(Birthday.isValidBirthday("95/01/01")); // YY/MM/DD format

        // valid birthday
        assertTrue(Birthday.isValidBirthday("1995/10/10")); // YYYY/MM/DD
    }
}
