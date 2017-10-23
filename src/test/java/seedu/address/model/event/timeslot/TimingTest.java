//@@author A0162268B
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimingTest {
    @Test
    public void compareTo() throws Exception {
        Timing timeOne = new Timing("0000-1300");
        Timing timeTwo = new Timing("1300-1400");
        Timing timeThree = new Timing("1300-1500");
        Timing timeFour = new Timing("1900-2100");

        assertTrue(timeOne.compareTo(timeTwo) < 0);
        assertTrue(timeTwo.compareTo(timeThree) == 0);
        assertTrue(timeThree.compareTo(timeFour) < 0);
        assertTrue(timeFour.compareTo(timeOne) > 0);

        assertFalse(Timing.isValidTiming(""));
        assertFalse(Timing.isValidTiming(" "));
        assertFalse(Timing.isValidTiming("1"));
        assertFalse(Timing.isValidTiming("1100"));
        assertFalse(Timing.isValidTiming("1-1"));
        assertFalse(Timing.isValidTiming("12-12"));
        assertFalse(Timing.isValidTiming("123-123"));
        assertFalse(Timing.isValidTiming("12345-12345"));
        assertFalse(Timing.isValidTiming("2500-2600")); // cannot accept non-24 hour times
        assertFalse(Timing.isValidTiming("1200-2400")); // 2400 hrs does not exist

        assertTrue(Timing.isValidTiming("0000-2359"));
        assertTrue(Timing.isValidTiming("1900-2100"));
    }
}

