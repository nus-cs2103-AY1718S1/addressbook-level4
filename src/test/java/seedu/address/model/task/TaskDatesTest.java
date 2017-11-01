package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskDatesTest {
    @Test
    public void isValid() {
        // invalid dates
        assertFalse(TaskDates.isDateValid("13-03-2019")); // invalid month in dashed format
        assertFalse(TaskDates.isDateValid("13.03.2019")); // invalid month in dotted format
        assertFalse(TaskDates.isDateValid("13/03/2019")); // invalid month in slashed format
        assertFalse(TaskDates.isDateValid("13.3.19")); // invalid month in contracted form
        assertFalse(TaskDates.isDateValid("02-30-19")); // invalid number of days in February
        assertFalse(TaskDates.isDateValid("3-32-19")); // invalid number of days in month

        // valid dates
        assertTrue(TaskDates.isDateValid("")); // empty string
        assertTrue(TaskDates.isDateValid(" ")); // string with only whitespace
        assertTrue(TaskDates.isDateValid("12.03.2014")); // valid date in dotted format
        assertTrue(TaskDates.isDateValid("12/3/14")); // valid date in slashed format
        assertTrue(TaskDates.isDateValid("12-3-2014")); // valid date in dashed format

    }
}
