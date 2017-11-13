package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author RonakLakhotia
public class DueDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DueDate.isValidDate("29.02.2001"));
        assertFalse(DueDate.isValidDate("31.04.2000"));
        assertFalse(DueDate.isValidDate("1.13.2000"));
        assertFalse(DueDate.isValidDate("1*1*2000"));

        //valid date
        assertTrue(DueDate.isValidDate("1.1.1997"));
        assertTrue(DueDate.isValidDate("1-1-1997"));

    }
}
