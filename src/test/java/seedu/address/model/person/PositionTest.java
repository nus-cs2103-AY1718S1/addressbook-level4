//@@author sebtsh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PositionTest {
    @Test
    public void isValidPosition() {
        // invalid positions
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only

        // valid positions
        assertTrue(Position.isValidPosition("Manager"));
        assertTrue(Position.isValidPosition("-")); // one character
        // long position
        assertTrue(Position.isValidPosition("Production Executive Operational Second Assistant Director"));
    }
}
