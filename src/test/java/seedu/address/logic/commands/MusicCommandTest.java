package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;

//@@author hanselblack
public class MusicCommandTest {
    @Before
    public void setUp() {
        final JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void execute_stopPauseMusicWithoutPlayer() {
        CommandResult result = new MusicCommand("stop").execute();
        assertEquals(MusicCommand.MESSAGE_NO_MUSIC_PLAYING, result.feedbackToUser);

        result = new MusicCommand("pause").execute();
        assertEquals(MusicCommand.MESSAGE_NO_MUSIC_PLAYING, result.feedbackToUser);
    }

    @Test
    public void execute_playMusic() {
        CommandResult result = new MusicCommand("play", "pop").execute();
        assertEquals("POP Music Playing", result.feedbackToUser);


        result = new MusicCommand("play", "dance").execute();
        assertEquals("DANCE Music Playing", result.feedbackToUser);

        result = new MusicCommand("play", "classic").execute();
        assertEquals("CLASSIC Music Playing", result.feedbackToUser);

    }
}
