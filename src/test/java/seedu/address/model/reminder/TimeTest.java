package seedu.address.model.reminder;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import seedu.address.model.reminders.Time;

//@@author justinpoh
public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid 24-hr time -> returns false
        assertFalse(Time.isValidTime("24:00"));
        assertFalse(Time.isValidTime("08:60"));

        // incorrect time input format -> returns false
        assertFalse(Time.isValidTime("8AM"));
        assertFalse(Time.isValidTime("0800"));

        // input is not even a time -> returns false
        assertFalse(Time.isValidTime("Not a time"));

        // valid time -> returns true
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("08:00"));
        assertTrue(Time.isValidTime("23:59"));
    }

    @Test
    public void equals() throws Exception {

        Time time1 = new Time("08:00");

        // different time -> returns false
        Time time2 = new Time("08:01");
        assertFalse(time1.equals(time2));

        // different type -> returns false
        assertFalse(time1.equals(100));

        // null -> returns false
        assertFalse(time1.equals(null));

        // same object -> returns true
        assertTrue(time1.equals(time1));

        // same value -> returns true
        Time time3 = new Time("08:00");
        assertTrue(time1.equals(time3));
    }
}
