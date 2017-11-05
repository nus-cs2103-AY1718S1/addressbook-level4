package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author jacoblipech
public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday();
        Birthday birthdayValue = new Birthday("12/04/97");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));
        assertTrue(birthdayValue.equals(birthdayValue));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday();
        Birthday birthdayValueCopy = new Birthday("12/04/97");
        assertTrue(birthday.equals(birthdayCopy));
        assertTrue(birthdayValue.equals(birthdayValueCopy));

        // different types -> returns false
        assertFalse(birthday.equals(true));
        assertFalse(birthdayValue.equals(true));

        // null -> returns false
        assertFalse(birthday == null);
        assertFalse(birthdayValue == null);

        // different values -> returns false
        Birthday differentBirthday = new Birthday("04/04/99");
        assertFalse(birthday.equals(differentBirthday));
        assertFalse(birthdayValue.equals(differentBirthday));
    }

    @Test
    public void isValidBirthday() {

        assertFalse(Birthday.isValidBirthdayFormat("234521893032")); // exceeding number limit
        assertFalse(Birthday.isValidBirthdayFormat("234")); // less than number required
        assertFalse(Birthday.isValidBirthdayFormat("231294")); // incorrect format

        // non integer used
        assertFalse(Birthday.isValidBirthdayFormat("example.com")); // invalid alphabet used
        assertFalse(Birthday.isValidBirthdayFormat(">.<??!")); // invalid random syntax used

        // incorrect day entered
        assertFalse(Birthday.isValidBirthdayFormat("32/05/94")); // day entered more than range
        assertFalse(Birthday.isValidBirthdayFormat("00/05/94")); // day entered less than range

        // incorrect month entered
        assertFalse(Birthday.isValidBirthdayFormat("23/13/94")); // month entered more than range
        assertFalse(Birthday.isValidBirthdayFormat("02/00/94")); // month entered less than range

        // incorrect year entered
        assertFalse(Birthday.isValidBirthdayFormat("23/13/0000")); // month entered more than range

        // valid birthday
        assertTrue(Birthday.isValidBirthdayFormat("04/05/98")); // 6 digits DD/MM/YY
        assertTrue(Birthday.isValidBirthdayFormat("04/05/1998")); // 8 digits DD/MM/YYYY
        assertTrue(Birthday.isValidBirthdayFormat(Birthday.DEFAULT_BIRTHDAY));  // non used

    }

}
