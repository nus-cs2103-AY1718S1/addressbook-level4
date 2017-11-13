//@@author siri99
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("11/01/1999"));
        assertTrue(Birthday.isValidBirthday("16/08/1968"));
        assertTrue(Birthday.isValidBirthday("21/04/1973"));
        assertTrue(Birthday.isValidBirthday("17/06/2006"));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty
        assertFalse(Birthday.isValidBirthday(" ")); // only space
        assertFalse(Birthday.isValidBirthday("1999/01  /11")); // extra space
        assertFalse(Birthday.isValidBirthday("1999/01/11")); // wrong format
        assertFalse(Birthday.isValidBirthday("11/January/1999")); // non-numeric
        assertFalse(Birthday.isValidBirthday("01.02#99")); // non-numeric characters
        assertFalse(Birthday.isValidBirthday("11/02/19899")); // invalid year
        assertFalse(Birthday.isValidBirthday("01/022/1973")); // invalid month
        assertFalse(Birthday.isValidBirthday("40/05/1998")); // invalid date
        assertFalse(Birthday.isValidBirthday("29/02/1989")); // invalid date(non-leap year does not have 29th Feb)

    }
}
//@@author siri99
