//@@author conantteo
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    private boolean validBirthday;

    @Test
    public void isValidBirthday() {
        // invalid birthday format
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("02/03/190")); // less than 8 digits
        assertFalse(Birthday.isValidBirthday("02/03/19000")); // more than 8 digits
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(validBirthday); // alphanumeric
        assertFalse(Birthday.isValidBirthday("02.03-1990")); // invalid characters between digits
        assertFalse(Birthday.isValidBirthday("02031990")); // exactly 8 digits without any forward slash
        assertFalse(Birthday.isValidBirthday("29/02/1900")); // invalid leap day

        // valid birthday format
        assertTrue(Birthday.isValidBirthday("29/02/2000")); // valid leap day
        assertTrue(Birthday.isValidBirthday("02/03/1990")); // DD/MM/YYYY format consists of 8 digits and 2 '/'
    }

    @Test
    public void isValidBirthdayMonth() {
        // invalid birthday month
        assertFalse(Birthday.isValidMonth("111")); // more than 2 digits
        assertFalse(Birthday.isValidMonth("1")); // less than 2 digits
        assertFalse(Birthday.isValidMonth("00")); // exactly 2 digits but less than the range of [01 to 12]
        assertFalse(Birthday.isValidMonth("13")); // exactly 2 digits but above the range of [01 to 12]

        // valid birthday month
        assertTrue(Birthday.isValidMonth("01")); // exactly 2 digits between 01 to 12
    }
}
