package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventDescriptionTest {
    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(EventDescription.isValidDesc("")); //empty string
        assertFalse(EventDescription.isValidDesc(" ")); //spaces only

        // valid description
        assertTrue(EventDescription.isValidDesc("tomorrow")); //alphabets only
        assertTrue(EventDescription.isValidDesc("12345")); //numbers only
        assertTrue(EventDescription.isValidDesc("tmr 12")); //alphabets and numbers
        assertTrue(EventDescription.isValidDesc("ths @")); // contains non-alphanumeric values
    }
}
