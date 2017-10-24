package seedu.address.logic.commands;

import java.io.File;
import java.util.Arrays;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    private static final String MESSAGE_MUSIC_STOP = "Music Stopped";

    private static String MESSAGE_MUSIC_PAUSE = "Music Paused";

    private static String MESSAGE_SUCCESS = "Music Playing";

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
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                return new CommandResult(MESSAGE_SUCCESS);
            }
            if (genreExist) {
                String musicFile = "audio/music/" + genre + trackNumber + ".mp3";
                MESSAGE_SUCCESS = genre.toUpperCase() + " Music Playing";
                if (trackNumber < 2) {
                    trackNumber++;
                } else {
                    trackNumber = 1;
                }
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                Media sound = new Media(new File(musicFile).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(2.0);
                mediaPlayer.play();
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_USAGE);
            }
        case "stop":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            return new CommandResult(MESSAGE_MUSIC_STOP);
        case "pause":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }
            MESSAGE_MUSIC_PAUSE = genre.toUpperCase() + " Music Paused";
            return new CommandResult(MESSAGE_MUSIC_PAUSE);
        default:
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
