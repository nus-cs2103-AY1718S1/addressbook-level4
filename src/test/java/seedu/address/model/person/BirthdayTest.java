//@@author inGall
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday("01/01/1991");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value);
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different person -> returns false
        Birthday differentBirthday = new Birthday("02/02/1992");
        assertFalse(birthday.equals(differentBirthday));

        // Non-existent birthday -> returns false
        assertFalse(Birthday.isValidBirthday("99/99/9999"));
    }

    @Test
    public void validBirthday() {
        // invalid date
        assertTrue(Birthday.isValidBirthday("")); // empty string
        assertTrue(Birthday.isValidBirthday("01/01/1991"));  // valid date

        assertFalse(Birthday.isValidBirthday("0d/fd/199g")); // contains non-numeric characters
        assertFalse(Birthday.isValidBirthday("10/28/1999 ")); // wrong date format
        assertFalse(Birthday.isValidBirthday("1/1/1994")); // Insufficient digits
        assertFalse(Birthday.isValidBirthday("40/01/1994")); // invalid day
        assertFalse(Birthday.isValidBirthday("01/16/1995")); // invalid month
        assertFalse(Birthday.isValidBirthday("01/01/20000")); // invalid year

    }
}
