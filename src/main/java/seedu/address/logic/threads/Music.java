package seedu.address.logic.threads;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import seedu.address.commons.core.LogsCenter;

//@@author hanselblack
/**
 * Creates a new thread to stream music source, this is to prevent UI thread from freezing
 */
public class Music extends Thread {


    private String audioFileName;
    private Player player;
    private final Logger logger = LogsCenter.getLogger(Music.class);

    public Music(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    /**
     * Opens buffer input stream to stream music source
     */
    public void run() {
        URL url = this.getClass().getClassLoader().getResource(audioFileName);
        try {
            //loop music
            while (true) {
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                player = new Player(in);
                player.play();
                in.close();
            }
        } catch (IOException e) {
            logger.info("Invalid IO for BufferedInputStream: " + audioFileName);
        } catch (JavaLayerException e) {
            logger.info("JavaLayerExeception: Invalid File Type for Music Player");
        }
    }
}

