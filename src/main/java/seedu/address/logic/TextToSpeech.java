package seedu.address.logic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

//@@author hanselblack
/**
 * Text To Speech, converts a string into audible speech audio.
 */
public class TextToSpeech {

    // Some available voices are (kevin, kevin16, alan)
    // alan only have a limited number of vocabulary hence
    // kevin16 is used for ensure all words can be turned into speech
    private static final String VOICE_NAME = "kevin16";

    private String word;

    public TextToSpeech(String word) {
        this.word = word;
    }

    /**
     * Execute the Text to Speech Function
     * and returns void.
     */
    public void speak() {
        //Async, by creating a new thread to prevent freezing on UI (main) thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            VoiceManager vm = VoiceManager.getInstance();
            Voice voice = vm.getVoice(VOICE_NAME);
            //Voice settings to adjust how it sound like
            voice.setRate(120f);
            voice.setPitchShift(1.5f);
            voice.setVolume(200f);
            voice.allocate();
            //Play the TextToSpeech Voice
            voice.speak(word);
        });
    }
}
