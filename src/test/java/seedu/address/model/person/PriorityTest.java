//@@author sebtsh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priorities
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("High")); // anything that is not H, M, or L

        // valid priorities. Only H, M, and L are valid priorities
        assertTrue(Priority.isValidPriority("H"));
        assertTrue(Priority.isValidPriority("M"));
        assertTrue(Priority.isValidPriority("L"));
    }
}
