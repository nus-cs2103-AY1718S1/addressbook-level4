package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Deadline.NO_DEADLINE_SET;

import org.junit.Test;

//@@author lawwman
public class DeadlineTest {

    @Test
    public void isValidDeadLine() {
        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("91")); // just 2 digits
        assertFalse(Deadline.isValidDeadline("dead line")); // non-numeric
        assertFalse(Deadline.isValidDeadline("91-20-30")); // incorrect format
        assertFalse(Deadline.isValidDeadline("21-21-2017")); // impossible date

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("11-11-2019")); // correct format
    }

    @Test
    public void compareTo() throws Exception {
        Deadline noDeadline = new Deadline(NO_DEADLINE_SET);
        Deadline earlyDeadline = new Deadline("01-01-2001");
        Deadline lateDeadline = new Deadline("31-12-2099");

        assertEquals(0, noDeadline.compareTo(noDeadline));
        assertEquals(1, noDeadline.compareTo(lateDeadline));
        assertEquals(-1, earlyDeadline.compareTo(noDeadline));
        assertEquals(-1, earlyDeadline.compareTo(lateDeadline));
        assertEquals(1, lateDeadline.compareTo(earlyDeadline));
    }
}
