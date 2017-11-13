package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Phone2Test {

    @Test
    //@@author chairz
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone2.isValidPhone("")); // empty string
        assertFalse(Phone2.isValidPhone(" ")); // spaces only
        assertFalse(Phone2.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone2.isValidPhone("phone")); // non-numeric
        assertFalse(Phone2.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone2.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone2.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone2.isValidPhone("93121534"));
        assertTrue(Phone2.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone2.isValidPhone("-")); //2nd Phone number is not available
    }
}
