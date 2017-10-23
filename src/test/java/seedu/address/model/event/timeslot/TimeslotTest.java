package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeslotTest {
    @Test
    public void isValidTiming() throws Exception {
        // Valid timing
        assertTrue(Timeslot.isValidTiming("22/10/2017 1053-1055"));

        // Invalid day format
        assertFalse(Timeslot.isValidTiming("2/10/2017 1053-1055"));

        // Invalid month format
        assertFalse(Timeslot.isValidTiming("02/9/2017 1053-1055"));

        // Invalid year format
        assertFalse(Timeslot.isValidTiming("02/10/123 1053-1055"));

        //Invalid time format
        assertFalse(Timeslot.isValidTiming("02/10/2017 935-10"));
    }

}
