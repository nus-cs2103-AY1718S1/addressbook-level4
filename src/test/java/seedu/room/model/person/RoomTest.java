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
        assertFalse(Room.isValidRoom("-")); // one character
        assertFalse(Room.isValidRoom("123-1234")); // long room

        // valid rooms
        assertTrue(Room.isValidRoom("09-100"));

        // default empty room
        assertTrue(Room.isValidRoom("Not Set"));
    }
}
