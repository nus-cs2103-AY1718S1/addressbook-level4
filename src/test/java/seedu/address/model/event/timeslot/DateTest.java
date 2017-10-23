//@@author A0162268B
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTest {
    @Test
    public void gregorianDatesTest() throws IllegalValueException {
        assertFalse(isGregorianDate("29/02/2017"));
        assertFalse(isGregorianDate("00/02/2017"));
        assertFalse(isGregorianDate("07/00/2017"));
        assertTrue(isGregorianDate("23/10/2017"));
        assertTrue(isGregorianDate("29/02/2020"));
    }

    /**
     * Returns true if input date argument is a valid date in gregorian calendar.
     */
    public boolean isGregorianDate(String date) {
        try {
            new Date(date);
        } catch (IllegalValueException e) {
            return false;
        }
        return true;
    }


    @Test
    public void compareTo() throws Exception {
        Date one = new Date("22/10/2017");
        Date two = new Date("21/11/2017");
        Date three = new Date("22/12/2018");

        assertTrue(one.compareTo(two) < 0);
        assertTrue(two.compareTo(three) < 0);
        assertTrue(three.compareTo(one) > 0);
        assertTrue(one.compareTo(one) == 0);
    }

}
