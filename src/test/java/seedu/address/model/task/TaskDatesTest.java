package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskDatesTest {
    @Test
    public void isValid() throws Exception {
        // blank start date
        assertFalse(TaskDates.isDateValid("")); //empty string
        assertFalse(TaskDates.isDateValid(" ")); //spaces only

        // wrong date format
        assertFalse(TaskDates.isDateValid("20/09/2020"));
        assertFalse(TaskDates.isDateValid("20.09.2020"));
        assertFalse(TaskDates.isDateValid("2-09-2020"));
        assertFalse(TaskDates.isDateValid("2-9-2020"));
        assertFalse(TaskDates.isDateValid("2-9-20"));
        assertFalse(TaskDates.isDateValid("2/09/2020"));
        assertFalse(TaskDates.isDateValid("2/9/2020"));
        assertFalse(TaskDates.isDateValid("2/9/20"));
        assertFalse(TaskDates.isDateValid("2.09.2020"));
        assertFalse(TaskDates.isDateValid("2.9.20"));
        assertFalse(TaskDates.isDateValid("2nd January 2020"));

        // wrong dates
        assertFalse(TaskDates.isDateValid("32-03-2019"));
        assertFalse(TaskDates.isDateValid("12-13-2019"));

        // valid date
        assertTrue(TaskDates.isDateValid("20-09-2020"));
        assertTrue(TaskDates.isDateValid("02-10-2020"));
    }
}
