package seedu.address.commons.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Provides sound for events
 */
public class AudioUtil {
    //@@author lancehaoh
    /**
     * Play a .wav format audio file that is stored in src/main/resources/images folder
     *
     * @param clipName name of a .wav format audio file e.g. "music.wav"
     */
    public void playClip(String clipName)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        InputStream is = getClass().getResourceAsStream("/audio/" + clipName);
        AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }
}
