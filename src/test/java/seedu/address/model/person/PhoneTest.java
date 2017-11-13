package seedu.address.model.person;

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
        assertFalse(Phone.isValidPhone("91")); // less than 8 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertFalse(Phone.isValidPhone("12429384")); // Exactly 8 numbers but does not start with 6,8 or 9

        // valid phone numbers
        assertTrue(Phone.isValidPhone("91199999")); // Start with 9
        assertTrue(Phone.isValidPhone("81199999")); // Start with 8
        assertTrue(Phone.isValidPhone("61199999")); // Start with 6
    }

    @Test
    public void emailHashCode() {
        try {
            Phone phone1 = new Phone("61199999");
            Phone phone2 = new Phone("81199999");

            //hashcode matches for same phone --> return true
            assertTrue(phone1.hashCode() == phone1.hashCode());

            //hashcode don't match for different phone --> return false
            assertFalse(phone1.hashCode() == phone2.hashCode());

        } catch (IllegalValueException e) {
            assert false : "shouldn't hit this case at all";
        }
    }
}
