package seedu.address.model.reminder;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import seedu.address.model.reminders.Date;

//@@author justinpoh
public class DateTest {

    @Test
    public void isValidDate() {
        // day out-of-bounds -> returns false
        assertFalse(Date.isValidDate("0/01/2017"));
        assertFalse(Date.isValidDate("31/04/2017"));

        // month out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/13/2017"));
        assertFalse(Date.isValidDate("01/00/2017"));

        // year out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/13/2100"));
        assertFalse(Date.isValidDate("01/13/1899"));

        // incorrect date input format -> returns false
        assertFalse(Date.isValidDate("1 Mar 2017"));

        // input is not even a date -> returns false
        assertFalse(Date.isValidDate("Not a date"));

        // invalid leap day -> returns false
        assertFalse(Date.isValidDate("29/02/2017"));

        // valid leap day -> returns true
        assertTrue(Date.isValidDate("29/02/2016"));

        // valid dates with paired separator -> returns true
        assertTrue(Date.isValidDate("01/01/2017"));
        assertTrue(Date.isValidDate("01.01.2017"));
        assertTrue(Date.isValidDate("01-01-2017"));

        // valid dates with flexible separator -> returns true
        assertTrue(Date.isValidDate("01/01.2017"));
        assertTrue(Date.isValidDate("01.01-2017"));
        assertTrue(Date.isValidDate("01-01/2017"));
    }

    @Test
    public void equals() throws Exception {

        Date date1 = new Date("01/01/2017");

        // different date -> returns false
        Date date2 = new Date("02/01/2017");
        assertFalse(date1.equals(date2));

        // different type -> returns false
        assertFalse(date1.equals(100));

        // null -> returns false
        assertFalse(date1.equals(null));

        // same object -> returns true
        assertTrue(date1.equals(date1));

        // same value -> returns true
        Date date3 = new Date("01/01/2017");
        assertTrue(date1.equals(date3));
    }
}
