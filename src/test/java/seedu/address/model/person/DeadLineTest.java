package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadLineTest {

    @Test
    public void isValidDeadLine() {
        // invalid dead lines
        assertFalse(DeadLine.isValidDeadLine("")); // empty string
        assertFalse(DeadLine.isValidDeadLine(" ")); // spaces only
        assertFalse(DeadLine.isValidDeadLine("91")); // just 2 digits
        assertFalse(DeadLine.isValidDeadLine("dead line")); // non-numeric
        assertFalse(DeadLine.isValidDeadLine("91-20-30")); // incorrect format
        assertFalse(DeadLine.isValidDeadLine("21-21-2017")); // impossible date

        // valid dead lines
        assertTrue(DeadLine.isValidDeadLine("11-11-2019")); // correct format
    }
}
