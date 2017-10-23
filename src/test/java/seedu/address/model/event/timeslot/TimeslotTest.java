//@@author A0162268B
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeslotTest {
    private String validTimeOne = "22/10/2017 1053-1055";
    private String validTimeTwo = "22/10/2017 1032-1055";
    private String validTimeThree = "21/09/2017 1053-1055";
    private String validTimeFour = "22/10/2017 1053-2100";

    private String invalidDay = "2/10/2017 1053-1055";
    private String invalidMonth = "02/9/2017 1053-1055";
    private String invalidYear = "02/9/123 1053-1055";
    private String invalidTime = "02/10/2017 935-10";

    @Test
    public void compareTo() throws Exception {
        try {
            Timeslot one = new Timeslot(validTimeOne);
            Timeslot two = new Timeslot(validTimeTwo);
            Timeslot three = new Timeslot(validTimeThree);
            Timeslot four = new Timeslot(validTimeFour);

            assertTrue(one.compareTo(two) > 0);
            assertTrue(two.compareTo(three) > 0);
            assertTrue(three.compareTo(four) < 0);
            assertTrue(one.compareTo(four) == 0);
        } catch (IllegalValueException e) {
            // Should not happen
            throw e;
        }

    }

    @Test
    public void isValidTiming() throws Exception {
        // Valid timing
        assertTrue(Timeslot.isValidTiming("22/10/2017 1053-1055"));

        // Invalid day format
        assertFalse(Timeslot.isValidTiming(invalidDay));

        // Invalid month format
        assertFalse(Timeslot.isValidTiming(invalidMonth));

        // Invalid year format
        assertFalse(Timeslot.isValidTiming(invalidYear));

        //Invalid time format
        assertFalse(Timeslot.isValidTiming(invalidTime));
    }

}
