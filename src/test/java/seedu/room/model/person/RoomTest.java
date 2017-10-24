package seedu.room.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoomTest {

    @Test
    public void isValidRoom() {
        // invalid rooms
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only

        // valid rooms
        assertTrue(Room.isValidRoom("Blk 456, Den Road, #01-355"));
        assertTrue(Room.isValidRoom("-")); // one character
        assertTrue(Room.isValidRoom("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long room

        // default empty room
        assertTrue(Room.isValidRoom("Not Set"));
    }
}
