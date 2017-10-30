package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTimeTest {
    @Test
    public void isValidDateTime() {
        // invalid DateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only

        // valid DateTime
        assertTrue(DateTime.isValidDateTime("21-12-2018 16:00"));
    }
}
