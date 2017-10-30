package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday();
        Birthday birthdayValue = new Birthday("120497");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));
        assertTrue(birthdayValue.equals(birthdayValue));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday();
        Birthday birthdayValueCopy = new Birthday("120497");
        assertTrue(birthday.equals(birthdayCopy));
        assertTrue(birthdayValue.equals(birthdayValueCopy));

        // different types -> returns false
        assertFalse(birthday.equals(true));
        assertFalse(birthdayValue.equals(true));

        // null -> returns false
        assertFalse(birthday.equals(null));
        assertFalse(birthdayValue.equals(null));

        // different values -> returns false
        Birthday differentBirthday = new Birthday("040499");
        assertFalse(birthday.equals(differentBirthday));
        assertFalse(birthdayValue.equals(differentBirthday));
    }

    @Test
    public void isValidBirthday() {

        assertFalse(Birthday.isValidBirthdayNumber("234521893032")); // exceeding number limit

        // non integer used
        assertFalse(Birthday.isValidBirthdayNumber("example.com")); // invalid alphabet used
        assertFalse(Birthday.isValidBirthdayNumber(">.<??!")); // invalid random syntax used

        // valid birthday
        assertTrue(Birthday.isValidBirthdayNumber("040598"));
        assertTrue(Birthday.isValidBirthdayNumber(Birthday.DEFAULT_BIRTHDAY));  // non used

    }

}
