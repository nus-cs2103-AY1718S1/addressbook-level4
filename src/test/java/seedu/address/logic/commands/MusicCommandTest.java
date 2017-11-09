package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


//@@author hanselblack
public class MusicCommandTest {

    @Test
    public void equals() {

        MusicCommand musicFirstCommand = new MusicCommand("play");

        // same object -> returns true
        assertTrue(musicFirstCommand.equals(musicFirstCommand));

        // same values -> returns true
        MusicCommand emailFirstCommandCopy = new MusicCommand("play");
        assertTrue(musicFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(musicFirstCommand.equals(1));

        // null -> returns false
        assertFalse(musicFirstCommand.equals(null));
    }
}
