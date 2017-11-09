package seedu.address.model.parcel;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class DeliveryDateTest {

    //@@author fustilio
    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(DeliveryDate.isValidDate("")); // empty string
        assertFalse(DeliveryDate.isValidDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDate("91")); // less than 3 numbers
        assertFalse(DeliveryDate.isValidDate("9321313213213123212131")); // only numbers, can't understand
        assertFalse(DeliveryDate.isValidDate("a")); // short string
        assertFalse(DeliveryDate.isValidDate("date")); // non-numeric
        assertFalse(DeliveryDate.isValidDate("#(_!@!@(")); // special charactors
        assertFalse(DeliveryDate.isValidDate("\u200E\uD83D\uDE03\uD83D\uDC81")); // emojis
        assertFalse(DeliveryDate.isValidDate("I love cs2103T")); // non date sentence
        assertFalse(DeliveryDate.isValidDate("32-05-1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05-13-1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("32/05/1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05/13/1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("32.05.1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05.13.1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("29.02.2001")); // Not a leap year
        assertFalse(DeliveryDate.isValidDate("0.02.2001")); // single digit but wrong day
        assertFalse(DeliveryDate.isValidDate("29.0.2001")); // single digit but wrong month

        // valid dates
        assertTrue(DeliveryDate.isValidDate("20-12-1990")); // exactly 3 numbers
        assertTrue(DeliveryDate.isValidDate("01-01-2001"));
        assertTrue(DeliveryDate.isValidDate("01/01/2001"));
        assertTrue(DeliveryDate.isValidDate("01.01.2001"));
        assertTrue(DeliveryDate.isValidDate("31.1.2001")); // single digit month
        assertTrue(DeliveryDate.isValidDate("1.01.2001")); // single digit day
        assertTrue(DeliveryDate.isValidDate("1.1.2001")); // single digit day and month
        assertTrue(DeliveryDate.isValidDate("29.02.2004")); // is leap year

        // invalid dates but returns true because parser "understands" it
        assertTrue(DeliveryDate.isValidDate("9011p041")); // alphabets within digits
        assertTrue(DeliveryDate.isValidDate("9312 1534")); // spaces within digits
    }

    @Test
    public void isPrettyTimeAccurate() throws IllegalValueException {
        assertEquals(new DeliveryDate("01-01-2001"), new DeliveryDate("First day of 2001"));
        assertEquals(new DeliveryDate("02-08-2017"), new DeliveryDate("Second day of August 2017"));
        assertEquals(new DeliveryDate("4-7-2017"), new DeliveryDate("independence day 2017"));
        assertEquals(new DeliveryDate("14-2-2017"), new DeliveryDate("Valentines day 2017"));
        assertEquals(new DeliveryDate("24-12-2017"), new DeliveryDate("Christmas eve 2017"));
    }
    //@@author

}
