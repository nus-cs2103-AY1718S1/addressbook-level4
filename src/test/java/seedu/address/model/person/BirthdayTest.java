package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author CindyTsai1
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("1324")); // less than 8 digits
        assertFalse(Birthday.isValidBirthday("dgd")); // non-numeric
        assertFalse(Birthday.isValidBirthday("23/05/1997")); // invalid slash
        assertFalse(Birthday.isValidBirthday("23 05 1997")); // invalid space
        assertFalse(Birthday.isValidBirthday("99999999")); //invalid dates
        assertFalse(Birthday.isValidBirthday("00000000")); //invalid dates

        // valid birthdays/ empty when optional
        assertTrue(Birthday.isValidBirthday("")); // empty string
        assertTrue(Birthday.isValidBirthday("23051997"));
    }
}
