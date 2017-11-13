package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 7 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("0154361")); // starts with 0
        assertFalse(Phone.isValidPhone("+91")); // less than 7 numbers, with +
        assertFalse(Phone.isValidPhone("+0154361")); // starts with 0, with +

        // valid phone numbers
        assertTrue(Phone.isValidPhone("9154361")); // exactly 7 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("+9154361")); // exactly 7 numbers, with +
        assertTrue(Phone.isValidPhone("+93121534")); // with +
        assertTrue(Phone.isValidPhone("+124293842033123")); // long phone numbers, with +
    }
}
