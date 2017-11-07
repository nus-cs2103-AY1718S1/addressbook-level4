package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {
    //@@author chairz
    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("birthday*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("birthday")); // alphabets only
        assertTrue(Name.isValidName("21")); // numbers only
        assertTrue(Name.isValidName("21st birthday")); // alphanumeric characters
        assertTrue(Name.isValidName("21ST Birthday")); // with capital letters
        assertTrue(Name.isValidName("21st Birthday Party is the best party")); // long names
    }
}
