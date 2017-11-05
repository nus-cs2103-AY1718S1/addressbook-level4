//@@author Lenaldnwj
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
        assertFalse(Phone.isValidPhone("9797979")); // number do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("97jhb971")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("gfxgfxgf")); // purely alphabets

        // valid phone numbers
        assertTrue(Phone.isValidPhone("97272031")); // student phone number of exactly 8 numbers
        assertTrue(Phone.isValidPhone("87767988")); // student number only of exactly 8 digit numbers
    }

}
