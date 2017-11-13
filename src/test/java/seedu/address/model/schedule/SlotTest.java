package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author YuchenHe98
public class SlotTest {

    @Test
    public void isValidSlot() throws IllegalValueException {

        // blank input
        assertFalse(Slot.isValidSlot(new Day("Tuesday"), new Time("0700"), new Time("0700")));
        assertFalse(Slot.isValidSlot(new Day("Tuesday"), new Time("1900"), new Time("1830")));
        // valid days
        assertTrue(Slot.isValidSlot(new Day("Tuesday"), new Time("0600"), new Time("2330")));
    }
}
