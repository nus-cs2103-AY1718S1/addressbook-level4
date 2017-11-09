package seedu.address.logic.commands;

import java.util.Arrays;

import javafx.scene.media.MediaPlayer;
import seedu.address.logic.Radio;

//@@author hanselblack
/**
 * Plays Music with music play command
 * Pause Music with music pause command
 * Stop Music with music stop command
 */
public class RadioCommand extends Command {

    public static final String COMMAND_WORD = "radio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/pause/stop music "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play, pause or stop) "
            + "GENRE (must be either pop, dance or classic) \n"
            + "Example: " + COMMAND_WORD + " play classic ";

    private static final String MESSAGE_STOP = "Radio Stopped";

    private static String messageSuccess = "Radio Playing";

    private static MediaPlayer mediaPlayer;
    private static Radio music;

    private static int trackNumber = 1;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"chinese", "classic", "comedy", "country", "news", "pop"};

    public RadioCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public RadioCommand(String command) {
        this.command = command;
    }

    /**
     * Returns whether music is currently playing.
     */
    public static boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    @Override
    public CommandResult execute() {

        boolean genreExist = Arrays.asList(genreList).contains(genre);
        switch (command) {
            case "play":
                if (music != null) {
                    music.stop();
                }
                if (genreExist) {
                    music = new Radio(genre);
                    music.start();

                    messageSuccess = genre.toUpperCase() + " Radio Playing";
                    return new CommandResult(messageSuccess);
                }
                return new CommandResult(MESSAGE_USAGE);
            case "stop":
                music.stop();
                return new CommandResult(MESSAGE_STOP);
            default:
                return new CommandResult(MESSAGE_USAGE);
        }
    }
}


