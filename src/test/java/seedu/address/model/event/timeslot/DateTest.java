package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author reginleiff
public class DateTest {

    private Date TEST_DATE_ONE;
    private Date TEST_DATE_TWO;
    private Date TEST_DATE_THREE;
    private Date TEST_DATE_FOUR;
    private Date TEST_DATE_FIVE;
    private Date TEST_DATE_SIX;
    private Date TEST_DATE_SEVEN;
    private Date TEST_DATE_EIGHT;

    public DateTest() {
        try {
            TEST_DATE_ONE = new Date("22/10/2017");
            TEST_DATE_TWO = new Date("21/11/2017");
            TEST_DATE_THREE = new Date("22/12/2018");
            TEST_DATE_FOUR = new Date("30/10/2017");
            TEST_DATE_FIVE = new Date("06/11/2017");
            TEST_DATE_SIX = new Date("07/11/2017");
            TEST_DATE_SEVEN = new Date("31/12/2017");
            TEST_DATE_EIGHT = new Date("01/01/2018");
        } catch (IllegalValueException e) {
            assert false: "Not supposed to occur";
        }
    }

    @Test
    public void addDays_success() throws Exception {
        // Within month
        assertEquals(TEST_DATE_FIVE.addDays(1), TEST_DATE_SIX);

        // Bypassing months
        assertEquals(TEST_DATE_FOUR.addDays(7), TEST_DATE_FIVE);

        // Bypassing years
        assertEquals(TEST_DATE_SEVEN.addDays(1), TEST_DATE_EIGHT);
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


        assertTrue(TEST_DATE_ONE.compareTo(TEST_DATE_TWO) < 0);
        assertTrue(TEST_DATE_TWO.compareTo(TEST_DATE_THREE) < 0);
        assertTrue(TEST_DATE_THREE.compareTo(TEST_DATE_ONE) > 0);
        assertTrue(TEST_DATE_ONE.compareTo(TEST_DATE_ONE) == 0);
    }

}
