package seedu.address.logic;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

//@@author hanselblack
/**
 * Common class to play audio file such as mp3 file etc.
 */
public class Audio {

    private String audioFileName;
    private Player player;

    public Audio(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    /**
     * Plays audio file, returns void
     */
    public void playSound() {
        URL url = this.getClass().getClassLoader().getResource(audioFileName);
        //Async, by creating a single thread to prevent freezing on UI (main) thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                player = new Player(in);
                player.play();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
    }
}
