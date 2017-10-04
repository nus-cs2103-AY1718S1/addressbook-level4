package seedu.address.logic.commands;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Lists all persons in the address book to the user.
 */
public class MusicCommand extends Command {

    public static final String COMMAND_WORD = "music";

    public static final String MESSAGE_SUCCESS = "Music Playing";

    private static MediaPlayer mediaPlayer;

    @Override
    public CommandResult execute() {
        int randomNum = 1 + (int) (Math.random() * 1);
        String musicFile = "audio/music/mainmenutheme" + randomNum + ".mp3";
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(50.0);
        mediaPlayer.play();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
