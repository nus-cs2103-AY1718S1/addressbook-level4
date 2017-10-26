package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DescriptionTest {
    @Test
    public void isValid() {
        //invalid description
        assertFalse(Description.isValidDescription("")); //empty string
        assertFalse(Description.isValidDescription(" ")); //string with only whitespace
        assertFalse(Description.isValidDescription("???")); //string with only symbols
        assertFalse(Description.isValidDescription("/do this/")); //string beginning with symbols
        assertFalse(Description.isValidDescription("do this/")); //string ending in symbol
        assertFalse(Description.isValidDescription("do'/\"this")); //string containing any symbol

        //valid description
        assertTrue(Description.isValidDescription("Self evaluation for CS2103T"));
        assertTrue(Description.isValidDescription("a")); //only 1 char
        assertTrue(Description.isValidDescription("This is a long description and i am not sure "
            + "what to type now but i still need to continue typing")); //long description
    }
}
