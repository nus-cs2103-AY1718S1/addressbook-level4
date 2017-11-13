package seedu.address;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import seedu.address.commons.core.LogsCenter;

//@@author justintkj
/**
 * Plays a beep Sound.
 */
public class Sound {
    public static final String EMPTY = "";
    public static final String ERROR_SOUND_LOG_MESSAGE = "Error with playing sound.";

    private static final Logger logger = LogsCenter.getLogger("Error Sound");

    private static ArrayList<String> musicList = new ArrayList<String>(Arrays.asList("ErrorSound.mp3"));
    private static int curr = 0;
    private static String bip;
    private static Media hit;
    private static MediaPlayer mediaPlayer;

    /**
     * start playing the first error invalidCommandSound on the playlist.
     */
    public static void invalidCommandSound() {
        try {
            createsNewMediaPlayer();
            mediaPlayer.play();
        } catch (Exception ex) {
            logger.info(ERROR_SOUND_LOG_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Generates a new MediaPlayer
     * @throws URISyntaxException if media file cannot be found
     */
    private static void createsNewMediaPlayer() throws URISyntaxException {
        bip = musicList.get(curr);
        //must be a valid file name before begin searching
        assert bip != EMPTY;
        hit = new Media(Thread.currentThread().getContextClassLoader().getResource(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }
}
