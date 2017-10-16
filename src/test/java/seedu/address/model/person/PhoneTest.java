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
        assertFalse(Phone.isValidPhone("student/97272031 parent/9797979")); // parent do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student/9727 parent/979")); // both do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student/9727 parent/97979797")); // student do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student:97272031 parent:97979797")); // use : instead of /
        assertFalse(Phone.isValidPhone("studen/97272031 paret/97979797")); // wrong labelling
        assertFalse(Phone.isValidPhone("student/97272031   parent/97979797")); // consecutive whitespaces
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("student/972jb72031 parent/97jhb979797")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("student/gfxgfx parent/gfxgfxgfx")); // purely alphabets

        // valid phone numbers
        assertTrue(Phone.isValidPhone("student/97272031 parent/97979797")); // exactly 8 numbers
        assertTrue(Phone.isValidPhone("student/87272111 parent/87767988")); // another set of exact 8 digit numbers
    }
}
