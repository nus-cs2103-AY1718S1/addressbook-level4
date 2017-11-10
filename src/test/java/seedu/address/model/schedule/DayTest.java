package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author YuchenHe98
public class DayTest {

    @Test
    public void isValidDay() {

        // blank input
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay(" ")); // spaces only

        // wrong input
        assertFalse(Day.isValidDay("someday"));
        assertFalse(Day.isValidDay("1"));

        // valid days
        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("Tuesday"));
        assertTrue(Day.isValidDay("Wednesday"));
        assertTrue(Day.isValidDay("Thursday"));
        assertTrue(Day.isValidDay("Friday"));
        assertTrue(Day.isValidDay("Saturday"));
        assertTrue(Day.isValidDay("Sunday"));
    }
}
