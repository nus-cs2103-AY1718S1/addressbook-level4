package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PeriodTest {

    @Test
    public void isValidPeriodTest() {
        //invalid period
        assertFalse(Period.isValidPeriod("")); //empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("-1")); // negative number
        assertFalse(Title.isValidTitle("367")); // exceeding upper limit
        assertFalse(Title.isValidTitle("2.1")); // not integer number of days

        // valid title
        assertTrue(Title.isValidTitle("0")); // 0 days indicates no repeat
        assertTrue(Title.isValidTitle("366")); // maximum number of days
    }
}
