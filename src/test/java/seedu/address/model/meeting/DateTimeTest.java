package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author Melvin-leo
public class DateTimeTest {
    @Test
    public void isValidDateTime() {
        // invalid DateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("31-02-2018 15:00")); //leap year non existent date
        assertFalse(DateTime.isValidDateTime("31022018 15:00")); // wrong date structure

        // valid DateTime
        assertTrue(DateTime.isValidDateTime("21-12-2018 16:00"));
    }
}
