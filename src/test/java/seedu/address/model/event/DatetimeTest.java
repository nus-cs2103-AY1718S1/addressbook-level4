package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

//@@author kaiyu92
public class DatetimeTest {

    @Test
    public void isValidDate() {
        // invalid datetime
        assertFalse(Datetime.isValidDatetime("")); // empty string
        assertFalse(Datetime.isValidDatetime(" ")); // spaces only
        assertFalse(Datetime.isValidDatetime("123456789")); // numbers only
        assertFalse(Datetime.isValidDatetime("shakjhsa")); // characters only
        assertFalse(Datetime.isValidDatetime("test123")); // numbers and characters
        assertFalse(Datetime.isValidDatetime("1-09-2017 2010")); // invalid date format
        assertFalse(Datetime.isValidDatetime("90-09-2017 2010")); // invalid day
        assertFalse(Datetime.isValidDatetime("02-13-2017 2010")); // invalid month
        assertFalse(Datetime.isValidDatetime("02-09-17 2010")); // invalid year
        assertFalse(Datetime.isValidDatetime("02-09-2017 2510")); // invalid hour
        assertFalse(Datetime.isValidDatetime("02-09-2017 2065")); // invalid minute

        // valid datetime
        assertTrue(Datetime.isValidDatetime("02-09-2017 2015"));
    }
}
