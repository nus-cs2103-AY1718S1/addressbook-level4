package seedu.address.logic.commands;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Lists all persons in the address book to the user.
 */
public class MusicCommand extends Command {

    public static final String COMMAND_WORD = "music";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 "
            + "Likes to swim.";

    public static final String MESSAGE_SUCCESS = "Music Playing";

    private static MediaPlayer mediaPlayer;

    private String command;
    private String genre;

    public MusicCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public MusicCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandResult execute() {
        switch (command) {
        case "play":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                int randomNum = 1 + (int) (Math.random() * 1);
                String musicFile = "audio/music/mainmenutheme" + randomNum + ".mp3";
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                Media sound = new Media(new File(musicFile).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(5.0);
                mediaPlayer.play();
                return new CommandResult(MESSAGE_SUCCESS);
            }
        case "stop":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            return new CommandResult(MESSAGE_SUCCESS);
        case "pause":
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }
            return new CommandResult(MESSAGE_SUCCESS);
        default:
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
