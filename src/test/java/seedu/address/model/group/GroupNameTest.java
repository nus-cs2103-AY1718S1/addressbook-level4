package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GroupNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(GroupName.isValidName("")); // empty string
        assertFalse(GroupName.isValidName(" ")); // spaces only
        assertFalse(GroupName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(GroupName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(GroupName.isValidName("peter jack")); // alphabets only
        assertTrue(GroupName.isValidName("12345")); // numbers only
        assertTrue(GroupName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(GroupName.isValidName("Capital Tan")); // with capital letters
        assertTrue(GroupName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
