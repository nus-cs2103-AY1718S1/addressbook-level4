//@@author erik0704
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DatesTest {
    @Test
    public void isValidDates() {
        // invalid dates
        assertFalse(Dates.isValidDateFormat("")); //empty string
        assertFalse(Dates.isValidDateFormat("  ")); //white string
        assertFalse(Dates.isValidDateFormat("?")); //non-numeric
        assertFalse(Dates.isValidDateFormat("20101213")); //no dash
        assertFalse(Dates.isValidDateFormat("17-05-25")); //wrong format, year only 2 digits
        // valid dates
        assertTrue(Dates.isValidDateFormat("2017-05-25"));
        assertTrue(Dates.isValidDateFormat("0001-01-21"));
    }

}
