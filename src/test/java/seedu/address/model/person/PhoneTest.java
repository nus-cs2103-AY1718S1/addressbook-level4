package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    // @@author donjar
    @Test
    public void extractPhone() throws IllegalValueException {
        assertEquals("93121534", Phone.extractPhone("9312 1534")); // spaces within digits
        assertEquals("93121534", Phone.extractPhone("9312-1534")); // dashes within digits
        assertEquals("93121534", Phone.extractPhone("(9312) 1534")); // parentheses within digits
        assertEquals("1234567890", Phone.extractPhone("(123) 456-7890")); // complex phone number
    }
    // @@author
}
