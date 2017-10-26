package seedu.room.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimestampTest {

    @Test
    public void isValidTimestamp() {
        // invalid Timestamp
        assertFalse(Timestamp.isValidTimestamp(-1));
        assertFalse(Timestamp.isValidTimestamp(-2));
        assertFalse(Timestamp.isValidTimestamp(-10000));

        //valid Timestamp
        assertTrue(Timestamp.isValidTimestamp(1));
        assertTrue(Timestamp.isValidTimestamp(1000));
        assertTrue(Timestamp.isValidTimestamp(5));

    }
}
