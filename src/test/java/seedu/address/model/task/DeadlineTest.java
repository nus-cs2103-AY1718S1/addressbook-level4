package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author raisa2010
public class DeadlineTest {

    @Test
    public void isValid() {
        // invalid dates
        assertFalse(DateTimeValidator.isDateValid("13-03-2019")); // invalid month in dashed format
        assertFalse(DateTimeValidator.isDateValid("13.03.2019")); // invalid month in dotted format
        assertFalse(DateTimeValidator.isDateValid("13/03/2019")); // invalid month in slashed format
        assertFalse(DateTimeValidator.isDateValid("13.3.19")); // invalid month in contracted form
        assertFalse(DateTimeValidator.isDateValid("02-30-19")); // invalid number of days in February
        assertFalse(DateTimeValidator.isDateValid("3-32-19")); // invalid number of days in month

        // valid dates
        assertTrue(DateTimeValidator.isDateValid("")); // empty string
        assertTrue(DateTimeValidator.isDateValid(" ")); // string with only whitespace
        assertTrue(DateTimeValidator.isDateValid("12.03.2014")); // valid date in dotted format
        assertTrue(DateTimeValidator.isDateValid("12/3/14")); // valid date in slashed format
        assertTrue(DateTimeValidator.isDateValid("12-3-2014")); // valid date in dashed format

    }
}
