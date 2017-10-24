package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.Group;

public class GroupTest {

    @Test
    public void isValidGroup() {
        // invalid phone numbers
        assertFalse(Group.isValidGroup("")); // empty string
        assertFalse(Group.isValidGroup(" ")); // spaces only
        assertFalse(Group.isValidGroup("phone")); // non-numeric
        assertFalse(Group.isValidGroup("9011p041")); // alphabets within digits
        assertFalse(Group.isValidGroup("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Group.isValidGroup("9")); // exactly 1 numbers
        assertTrue(Group.isValidGroup("93121534")); //more than 1 number
        assertTrue(Group.isValidGroup("124293842033123")); // long phone numbers
    }
}
