package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import javafx.scene.media.MediaPlayer;
import seedu.address.logic.TextToSpeech;
import seedu.address.logic.threads.Music;


//@@author hanselblack
/**
 * Plays Music with music play command
 * Pause Music with music pause command
 * Stop Music with music stop command
 */
public class MusicCommand extends Command {

    public static final String COMMAND_WORD = "music";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/pause/stop music "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play or stop) "
            + "GENRE (must be either pop, dance or classic) \n"
            + "Example: " + COMMAND_WORD + " play classic ";

    public static final String[] GENRE_LIST = {"pop", "dance", "classic"};
    public static final String MESSAGE_NO_MUSIC_PLAYING = "No music is currently playing";
    public static final String MESSAGE_STOP = "Music Stopped";
    private static final int maxTrackNumber = 2;

    private static String messagePause = "Music Paused";

    private static String messageSuccess = "Music Playing";
    private static MediaPlayer mediaPlayer;
    private static int trackNumber = 1;
    private static String previousGenre = "";
    private static Music music;

    private String command;
    private String genre = "pop";

    public MusicCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public MusicCommand(String command) {
        this.command = command;
    }

    /**
     * Returns true if music player is currently playing.
     * else return false
     */
    public static boolean isMusicPlaying() {
        if (music == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Stops music playing in the player
     */
    public static void stopMusicPlayer() {
        if (music != null) {
            music.stop();
        }
    }

    @Override
    public CommandResult execute() {
        boolean genreExist = Arrays.asList(GENRE_LIST).contains(genre);
        switch (command) {
        case "play":
            if (RadioCommand.isRadioPlaying()) {
                RadioCommand.stopRadioPlayer();
            }
            stopMusicPlayer();
            if (genreExist) {
                //check if current genre same previous playing music genre
                //if different reset track number
                if (!genre.equals(previousGenre)) {
                    trackNumber = 1;
                }
                messageSuccess = genre.toUpperCase() + " Music " + trackNumber + " Playing";
                music = new Music("audio/music/"
                        + genre + trackNumber + ".mp3");
                music.start();

                if (trackNumber < maxTrackNumber) {
                    trackNumber++;
                } else {
                    //reset track number back to 1
                    trackNumber = 1;
                }
                //Text to Speech
                new TextToSpeech(messageSuccess).speak();
                //set current playing genre as previousGenre
                previousGenre = genre;
                return new CommandResult(messageSuccess);
            } else {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
            }
        //Stop the music that is currently playing
        case "stop":
            if (!isMusicPlaying()) {
                //Text to Speech
                new TextToSpeech(MESSAGE_NO_MUSIC_PLAYING).speak();
                return new CommandResult(MESSAGE_NO_MUSIC_PLAYING);
            }
            stopMusicPlayer();
            //Text to Speech
            new TextToSpeech(MESSAGE_STOP).speak();
            return new CommandResult(MESSAGE_STOP);

        default:
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MusicCommand // instanceof handles nulls
                && (this.genre == null || this.genre.equals(((MusicCommand) other).genre)) // state check
                && (this.command == null || this.command.equals(((MusicCommand) other).command))); // state check
    }
}
