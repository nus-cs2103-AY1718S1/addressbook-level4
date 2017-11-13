package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PeriodTest {

    @Test
    public void isValidPeriodTest() {
        //invalid period
        assertFalse(Period.isValidPeriod("")); //empty string
        assertFalse(Period.isValidPeriod(" ")); // spaces only
        assertFalse(Period.isValidPeriod("-1")); // negative number
        assertFalse(Period.isValidPeriod("367")); // exceeding upper limit
        assertFalse(Period.isValidPeriod("2.1")); // not integer number of days

        // valid title
        assertTrue(Period.isValidPeriod("0")); // 0 days indicates no repeat
        assertTrue(Period.isValidPeriod("366")); // maximum number of days
    }
}
