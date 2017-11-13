package seedu.address.model.task;
//@@author Esilocke
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // invalid deadlines
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("alphabets")); // non-numeric letters
        assertFalse(Deadline.isValidDeadline("!@#$%^")); // invalid symbols

        // valid deadline (empty deadline for optional data)
        assertTrue(Deadline.isValidDeadline("")); // empty string

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("04-04-2017")); // dashes
        assertTrue(Deadline.isValidDeadline("the day after tomorrow")); // slashes
        assertTrue(Deadline.isValidDeadline("4-11"));
    }
}
