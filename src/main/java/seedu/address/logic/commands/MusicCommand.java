package seedu.address.logic.commands;

import java.util.Arrays;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
            + "Parameters: ACTION (must be either play, pause or stop) "
            + "GENRE (must be either pop, dance or classic) \n"
            + "Example: " + COMMAND_WORD + " play classic ";

    private static final String MESSAGE_STOP = "Music Stopped";

    private static String messagePause = "Music Paused";

    private static String messageNoMusicPlaying = "There is no music currently playing.";

    private static String messageSuccess = "Music Playing";

    private static MediaPlayer mediaPlayer;

    private static int trackNumber = 1;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"pop", "dance", "classic"};

    public MusicCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public MusicCommand(String command) {
        this.command = command;
    }

    /**
     * Returns whether music is currently playing.
     */
    public static boolean isMusicPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * Stops music playing in the mediaPlayer
     */
    public static void stopMusicPlayer() {
        if (isMusicPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public CommandResult execute() {
        boolean genreExist = Arrays.asList(genreList).contains(genre);
        switch (command) {
        case "play":
            RadioCommand.stopRadioPlayer();
            if (isMusicPlaying() && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                return new CommandResult(messageSuccess);
            } else if (!isMusicPlaying() && genreExist) {
                String musicFile = getClass().getResource("/audio/music/"
                        + genre + trackNumber + ".mp3").toExternalForm();
                messageSuccess = genre.toUpperCase() + " Music Playing";
                if (trackNumber < 2) {
                    trackNumber++;
                } else {
                    trackNumber = 1;
                }
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                Media sound = new Media(musicFile);
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(5.0);
                mediaPlayer.play();
                return new CommandResult(messageSuccess);
            } else {
                return new CommandResult(MESSAGE_USAGE);
            }
        case "stop":
            if (!isMusicPlaying()) {
                return new CommandResult(messageNoMusicPlaying);
            }
            stopMusicPlayer();
            return new CommandResult(MESSAGE_STOP);
        case "pause":
            if (!isMusicPlaying()) {
                return new CommandResult(messageNoMusicPlaying);
            }
            mediaPlayer.pause();
            messagePause = genre.toUpperCase() + " Music Paused";
            return new CommandResult(messagePause);

        default:
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
