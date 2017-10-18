package seedu.address.model.parcel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidDate("")); // empty string
        assertFalse(Phone.isValidDate(" ")); // spaces only
        assertFalse(Phone.isValidDate("91")); // less than 3 numbers
        assertFalse(Phone.isValidDate("phone")); // non-numeric
        assertFalse(Phone.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidDate("911")); // exactly 3 numbers
        assertTrue(Phone.isValidDate("93121534"));
        assertTrue(Phone.isValidDate("124293842033123")); // long phone numbers
    }
}
