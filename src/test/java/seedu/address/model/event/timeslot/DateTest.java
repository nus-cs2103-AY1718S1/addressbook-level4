package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author reginleiff
public class DateTest {

    private Date testDateOne;
    private Date testDateTwo;
    private Date testDateThree;
    private Date testDateFour;
    private Date testDateFive;
    private Date testDateSix;
    private Date testDateSeven;
    private Date testDateEight;

    public DateTest() {
        try {
            testDateOne = new Date("22/10/2017");
            testDateTwo = new Date("21/11/2017");
            testDateThree = new Date("22/12/2018");
            testDateFour = new Date("30/10/2017");
            testDateFive = new Date("06/11/2017");
            testDateSix = new Date("07/11/2017");
            testDateSeven = new Date("31/12/2017");
            testDateEight = new Date("01/01/2018");
        } catch (IllegalValueException e) {
            assert false : "Not supposed to occur";
        }
    }

    @Test
    public void addDays_success() throws Exception {
        // Within month
        assertEquals(testDateFive.addDays(1), testDateSix);

        // Bypassing months
        assertEquals(testDateFour.addDays(7), testDateFive);

        // Bypassing years
        assertEquals(testDateSeven.addDays(1), testDateEight);
    }

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


        assertTrue(testDateOne.compareTo(testDateTwo) < 0);
        assertTrue(testDateTwo.compareTo(testDateThree) < 0);
        assertTrue(testDateThree.compareTo(testDateOne) > 0);
        assertTrue(testDateOne.compareTo(testDateOne) == 0);
    }

}
