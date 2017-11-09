package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.RadioCommand.MESSAGE_USAGE;

import org.junit.Test;


//@@author hanselblack
public class RadioCommandTest {

    @Test
    public void execute_radio_wrongGenre() {
        RadioCommand radioCommand = new RadioCommand("play", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(MESSAGE_USAGE, commandResult.feedbackToUser);
    }

    @Test
    public void execute_music_wrongCommand() {
        RadioCommand radioCommand = new RadioCommand("wrongCommand", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(MESSAGE_USAGE, commandResult.feedbackToUser);

        radioCommand = new RadioCommand("wrongCommand");
        commandResult = radioCommand.execute();
        assertEquals(MESSAGE_USAGE, commandResult.feedbackToUser);
    }

    @Test
    public void equals() {

        RadioCommand radioFirstCommand = new RadioCommand("play");

        // same object -> returns true
        assertTrue(radioFirstCommand.equals(radioFirstCommand));

        // same values -> returns true
        RadioCommand emailFirstCommandCopy = new RadioCommand("play");
        assertTrue(radioFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(radioFirstCommand.equals(1));

        // null -> returns false
        assertFalse(radioFirstCommand.equals(null));
    }
}

