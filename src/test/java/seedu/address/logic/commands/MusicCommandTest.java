package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.embed.swing.JFXPanel;

//@@author hanselblack
public class MusicCommandTest {

    @Test
    public void execute_share_success() {
        final JFXPanel fxPanel = new JFXPanel();
        MusicCommand musicCommand = new MusicCommand("play");
        CommandResult commandResult = musicCommand.execute();
        assertEquals("POP Music Playing", commandResult.feedbackToUser);
    }
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
