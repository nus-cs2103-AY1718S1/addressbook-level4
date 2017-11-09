package seedu.room.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author Haozhe321
public class TimestampTest {

    @Test
    public void isValidInputTimestamp() {
        // invalid Timestamp
        assertFalse(Timestamp.isValidTimestamp(-1));
        assertFalse(Timestamp.isValidTimestamp(-2));
        assertFalse(Timestamp.isValidTimestamp(-10000));

        //valid Timestamp
        assertTrue(Timestamp.isValidTimestamp(0));
        assertTrue(Timestamp.isValidTimestamp(1));
        assertTrue(Timestamp.isValidTimestamp(1000));
        assertTrue(Timestamp.isValidTimestamp(5));

    }
}
