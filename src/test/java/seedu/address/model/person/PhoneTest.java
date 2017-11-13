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
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("123"));
        assertFalse(Phone.isValidPhone("124293842033123")); // long phone numbers

        // valid phone numbers
        assertTrue(Handphone.isValidPhone("93112534")); //exactly 8 numbers
        assertTrue(HomePhone.isValidPhone("63112534")); //exactly 8 numbers
        assertTrue(OfficePhone.isValidPhone("60112534")); //exactly 8 numbers
    }
}
