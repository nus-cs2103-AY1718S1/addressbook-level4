package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {
    //@@author chairz
    @Test
    public void isValidPriority() {
        // invalid description
        assertFalse(Priority.isValidPriority("5")); // number < 0
        assertFalse(Priority.isValidPriority("-5")); // number > 2


        // valid description
        assertTrue(Priority.isValidPriority("2")); // Priority 2


    }
}
