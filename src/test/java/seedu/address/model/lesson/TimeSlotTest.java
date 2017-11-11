package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.TimeSlot;

//@@author caoliangnus
public class TimeSlotTest {
    @Test
    public void isValidTimeSlot() {
        // invalid time slot
        assertFalse(TimeSlot.isValidTimeSLot("")); // empty string
        assertFalse(TimeSlot.isValidTimeSLot(" ")); // spaces only
        assertFalse(TimeSlot.isValidTimeSLot("FRI")); // no '['
        assertFalse(TimeSlot.isValidTimeSLot("FRI[]")); // no start time and end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1000]")); // no end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[10001200]")); // no '-'
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1200-1000]")); // start time less than end time

        // valid time slot
        assertTrue(TimeSlot.isValidTimeSLot("FRI[1000-1200]")); // Must follow this format
    }
}
