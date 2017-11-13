//@@author duyson98

package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("12345")); // less than 3 numbers
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("Pr90i0ori23ty")); // alphabets within digits
        assertFalse(Priority.isValidPriority("1234 5678")); // spaces within digits
        assertFalse(Priority.isValidPriority("LOW")); // capital letters
        assertFalse(Priority.isValidPriority("LoW")); // mixed non-capital and capital letters
        assertFalse(Priority.isValidPriority("Low Medium High")); // multiple priorities

        // valid priority
        assertTrue(Priority.isValidPriority("Low"));
        assertTrue(Priority.isValidPriority("Medium"));
        assertTrue(Priority.isValidPriority("High"));
    }
}
