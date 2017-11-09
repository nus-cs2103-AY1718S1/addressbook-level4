//@@author Lenaldnwj
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParentPhoneTest {

    @Test
    public void isValidPhone() {
        // invalid parent phone numbers
        assertFalse(ParentPhone.isValidParentPhone("")); // empty string
        assertFalse(ParentPhone.isValidParentPhone(" ")); // spaces only
        assertFalse(ParentPhone.isValidParentPhone("9797979")); // number do not have exactly 8 digit
        assertFalse(ParentPhone.isValidParentPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(ParentPhone.isValidParentPhone("97jhb971")); // alphanumeric numbers
        assertFalse(ParentPhone.isValidParentPhone("gfxgfxgf")); // purely alphabets

        // valid parent phone numbers
        assertTrue(ParentPhone.isValidParentPhone("97272031")); // parent phone number of exactly 8 numbers
        assertTrue(ParentPhone.isValidParentPhone("87767988")); // parent phone number of exactly 8 digit numbers
    }

}
