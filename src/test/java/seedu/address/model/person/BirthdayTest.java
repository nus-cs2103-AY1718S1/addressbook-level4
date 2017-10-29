package seedu.address.model.person;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class BirthdayTest {
    @Test
    public void isValidBirthday() {
        // invalid phone numbers
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("2001/2/11")); // not dd/mm/yyyy format

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("11/12/1998"));
        assertTrue(Birthday.isValidBirthday("12/01/1971"));
        assertTrue(Birthday.isValidBirthday("14/01/1986"));

        // birthday is not set
        assertTrue(Birthday.isValidBirthday("NIL"));
        assertTrue(Birthday.isValidBirthday(Birthday.BIRTHDAY_TEMPORARY));
    }

    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check name field value
        Birthday birthdayX = new Birthday("14/01/1986");
        Birthday birthdayY = new Birthday("14/01/1986");
        assertTrue(birthdayX.equals(birthdayY) && birthdayY.equals(birthdayX));
        assertTrue(birthdayX.hashCode() == birthdayY.hashCode());
    }
}



