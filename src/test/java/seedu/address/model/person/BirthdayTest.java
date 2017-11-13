package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author Valerieyue
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // Empty string
        assertFalse(Birthday.isValidBirthday("9188")); // Less than 6 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // Invalid date
        assertFalse(Birthday.isValidBirthday("9011p1")); // Alphabets within digits
        assertFalse(Birthday.isValidBirthday("931 534")); // Spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("12 12 87")); // Can enter year using 2 digit
        assertTrue(Birthday.isValidBirthday("14-05-1997")); // Accepts '-' as separator
        assertTrue(Birthday.isValidBirthday("08 aug 1985")); // Name of month
    }
}
