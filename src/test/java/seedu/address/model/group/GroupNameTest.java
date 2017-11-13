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
        assertFalse(GroupName.isValidName("chit-chat")); // contains non-alphanumeric characters

        // valid name
        assertTrue(GroupName.isValidName("group project")); // alphabets only
        assertTrue(GroupName.isValidName("123")); // numbers only
        assertTrue(GroupName.isValidName("testing123")); // alphanumeric characters
        assertTrue(GroupName.isValidName("Sample Group")); // with capital letters
        assertTrue(GroupName.isValidName("Group 1 2 Group 3")); // long names
    }
}
