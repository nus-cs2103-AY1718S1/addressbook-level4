package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RadioCommand.MESSAGE_NO_RADIO_PLAYING;

import org.junit.Test;

import seedu.address.logic.InternetConnectionCheck;

//@@author hanselblack
public class RadioCommandTest {

    @Test
    public void execute_radio_wrongGenre() {
        RadioCommand radioCommand = new RadioCommand("play", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_radio_wrongCommand() {
        RadioCommand radioCommand = new RadioCommand("wrongCommand", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        radioCommand = new RadioCommand("play", "nonExistedGenre");
        commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        radioCommand = new RadioCommand("wrongCommand");
        commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_stop_noExistingPlayer() {
        RadioCommand radioCommand = new RadioCommand("stop");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(MESSAGE_NO_RADIO_PLAYING, commandResult.feedbackToUser);

        radioCommand = new RadioCommand("stop", "additionalParameterGenre");
        commandResult = radioCommand.execute();
        assertEquals(MESSAGE_NO_RADIO_PLAYING, commandResult.feedbackToUser);
    }

    @Test
    public void execute_radio_successCommand() {
        String genre = "";
        RadioCommand radioCommand = new RadioCommand("play");
        CommandResult commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals("POP " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "pop";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "chinese";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "news";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "classic";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }
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

