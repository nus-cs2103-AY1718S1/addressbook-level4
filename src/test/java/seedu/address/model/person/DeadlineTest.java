package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadLine() {
        // invalid dead lines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("91")); // just 2 digits
        assertFalse(Deadline.isValidDeadline("dead line")); // non-numeric
        assertFalse(Deadline.isValidDeadline("91-20-30")); // incorrect format
        assertFalse(Deadline.isValidDeadline("21-21-2017")); // impossible date

        // valid dead lines
        assertTrue(Deadline.isValidDeadline("11-11-2019")); // correct format
    }
}
