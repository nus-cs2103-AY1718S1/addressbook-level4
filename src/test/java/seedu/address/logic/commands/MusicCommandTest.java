package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MusicCommand.MESSAGE_NO_MUSIC_PLAYING;

import org.junit.Test;

//@@author hanselblack
public class MusicCommandTest {

    @Test
    public void execute_music_wrongGenre() {
        MusicCommand musicCommand = new MusicCommand("play", "nonExistedGenre");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_music_wrongCommand() {
        MusicCommand musicCommand = new MusicCommand("wrongCommand", "nonExistedGenre");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        musicCommand = new MusicCommand("play", "nonExistedGenre");
        commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        musicCommand = new MusicCommand("wrongCommand");
        commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_stop_noExistingPlayer() {
        MusicCommand musicCommand = new MusicCommand("stop");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(MESSAGE_NO_MUSIC_PLAYING, commandResult.feedbackToUser);

        musicCommand = new MusicCommand("stop", "additionalParameterGenre");
        commandResult = musicCommand.execute();
        assertEquals(MESSAGE_NO_MUSIC_PLAYING, commandResult.feedbackToUser);
    }

    @Test
    public void execute_music_successCommand() {
        String genre = "pop";
        String trackNumber = "1";
        MusicCommand musicCommand = new MusicCommand("play");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "pop";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "dance";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "dance";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();
    }

    @Test
    public void execute_music_successNexTrack() {
        String genre = "pop";
        String trackNumber = "1";
        MusicCommand musicCommand = new MusicCommand("play", genre);
        CommandResult commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "pop";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();
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
