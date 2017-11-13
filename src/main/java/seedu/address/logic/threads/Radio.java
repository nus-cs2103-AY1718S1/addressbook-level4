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
 * Creates a new thread to stream radio source, this is to prevent UI thread from freezing
 */
public class Radio extends Thread {


    private String genre;
    private String radioStreamUrl;
    private Player player;
    private final Logger logger = LogsCenter.getLogger(Radio.class);

    public Radio(String genre) {
        this.genre = genre;
    }

    /**
     * Opens buffer input stream to stream radio source
     */
    public void run() {
        BufferedInputStream in = null;
        try {
            switch (genre) {
            case "chinese":
                radioStreamUrl = "http://198.105.214.140:2000/Live?icy=http";
                break;
            case "classic":
                radioStreamUrl = "http://media-sov.musicradio.com/ClassicFMMP3";
                break;
            case "news":
                radioStreamUrl = "http://streams.kqed.org/kqedradio?";
                break;
            case "pop":
                radioStreamUrl = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_q";
                break;
            default:
                radioStreamUrl = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_q";
                break;
            }

            in = new BufferedInputStream(new URL(radioStreamUrl).openStream());
            player = new Player(in);
            player.play();
            in.close();
        } catch (IOException e) {
            logger.info("Invalid IO for BufferedInputStream: " + radioStreamUrl);
        } catch (JavaLayerException e) {
            logger.info("JavaLayerExeception: Invalid File Type for Radio Player");
        }
    }
}

