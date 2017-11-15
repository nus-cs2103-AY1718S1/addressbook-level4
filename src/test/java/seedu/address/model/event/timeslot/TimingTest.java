package seedu.address.model.event.timeslot;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author reginleiff
public class TimingTest {
    private Timing timeOne;
    private Timing timeTwo;
    private Timing timeThree;
    private Timing timeFour;
    private Timing timeFive;
    private Timing timeSix;

    public TimingTest() {
        try {
            timeOne = new Timing("0000-1300");
            timeTwo = new Timing("1300-1400");
            timeThree = new Timing("1300-1500");
            timeFour = new Timing("1900-2100");
            timeFive = new Timing("1329-1937");
            timeSix = new Timing("1329-1950");
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }

    @Test
    public void getDurationInHours_correctness() {
        assertEquals(Timing.getDurationInHours(timeOne.getStart(), timeOne.getEnd()), 13.0);
        assertEquals(Timing.getDurationInHours(timeFive.getStart(), timeFive.getEnd()), 6.1);
        assertEquals(Timing.getDurationInHours(timeSix.getStart(), timeSix.getEnd()), 6.4);
    }

    @Test
    public void compareTo_correctness() {
        assertTrue(timeOne.compareTo(timeTwo) < 0);
        assertTrue(timeTwo.compareTo(timeThree) == 0);
        assertTrue(timeThree.compareTo(timeFour) < 0);
        assertTrue(timeFour.compareTo(timeOne) > 0);
    }

    @Test
    public void compareTo_invalid() {
        // Wrong formats
        assertFalse(Timing.isValidTiming(""));
        assertFalse(Timing.isValidTiming(" "));
        assertFalse(Timing.isValidTiming("1"));
        assertFalse(Timing.isValidTiming("1100"));
        assertFalse(Timing.isValidTiming("1-1"));
        assertFalse(Timing.isValidTiming("12-12"));
        assertFalse(Timing.isValidTiming("123-123"));
        assertFalse(Timing.isValidTiming("12345-12345"));

        // Impossible timings
        assertFalse(Timing.isValidTiming("2500-2600")); // cannot accept non-24 hour times
        assertFalse(Timing.isValidTiming("1200-2400")); // 2400 hrs does not exist
    }

    @Test
    public void compareTo_valid() {
        assertTrue(Timing.isValidTiming("0000-2359"));
        assertTrue(Timing.isValidTiming("1900-2100"));
    }
}

