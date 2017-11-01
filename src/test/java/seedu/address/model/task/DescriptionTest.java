package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tby1994
public class DescriptionTest {
    @Test
    public void isValid() {
        //invalid description
        assertFalse(Description.isValidDescription("")); //empty string
        assertFalse(Description.isValidDescription(" ")); //string with only whitespace
        assertFalse(Description.isValidDescription("???")); //string with only non-alphanumeric character
        assertFalse(Description.isValidDescription("/do this/")); //string containing non-alphanumeric character

        //valid description
        assertTrue(Description.isValidDescription("Self evaluation for class")); // only letters
        assertTrue(Description.isValidDescription("247")); // only numbers
        assertTrue(Description.isValidDescription("a")); // only 1 char
        assertTrue(Description.isValidDescription("DO NOT PROCRASTINATE")); // only capital letters
        assertTrue(Description.isValidDescription("finish the 2nd assignment")); // alphanumeric characters
        assertTrue(Description.isValidDescription("This is a long description and i am not sure "
            + "what to type now but i still need to continue typing")); // long description
    }
}
