//@@author yanji1221
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
        assertFalse(Birthday.isValidBirthday("^")); // only non-alphanumeric characters
        assertFalse(Birthday.isValidBirthday("1993")); // year only
        assertFalse(Birthday.isValidBirthday("19920102")); // not in the format yyyy/mm/dd

        // valid birthday
        assertTrue(Birthday.isValidBirthday("1993/12/12")); // int the format yyyy/mm/dd
    }
}
