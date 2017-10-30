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

        assertFalse(Birthday.isValidBirthdayFormat("234521893032")); // exceeding number limit
        assertFalse(Birthday.isValidBirthdayFormat("234")); // less than number required

        // non integer used
        assertFalse(Birthday.isValidBirthdayFormat("example.com")); // invalid alphabet used
        assertFalse(Birthday.isValidBirthdayFormat(">.<??!")); // invalid random syntax used

        // incorrect day entered
        assertFalse(Birthday.isValidDayEntered("320594")); // day entered more than range
        assertFalse(Birthday.isValidDayEntered("000594")); // day entered less than range

        // incorrect month entered
        assertFalse(Birthday.isValidMonthEntered("231394")); // month entered more than range
        assertFalse(Birthday.isValidMonthEntered("020094")); // month entered less than range

        // valid birthday
        assertTrue(Birthday.isValidBirthdayFormat("0405")); // 4 digits DDMM
        assertTrue(Birthday.isValidBirthdayFormat("040598")); // 6 digits DDMMYY
        assertTrue(Birthday.isValidBirthdayFormat("04051998")); // 8 digits DDMMYYYY
        assertTrue(Birthday.isValidDayEntered("040598"));
        assertTrue(Birthday.isValidMonthEntered("040598"));
        assertTrue(Birthday.isValidBirthdayFormat(Birthday.DEFAULT_BIRTHDAY));  // non used

    }

}
