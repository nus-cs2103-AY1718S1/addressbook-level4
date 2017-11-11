package seedu.address.logic.commands;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Text To Speech, converts a string into audible speech audio.
 */
public class TextToSpeech {

    // Some available voices are (kevin, kevin16, alan)
    private static final String VOICE_NAME = "kevin16";

    private String word;

    public TextToSpeech(String word) {
        this.word = word;
        execute();
    }

    /**
     * Execute the Text to Speech Function
     * and returns void.
     */
    public void execute() {
        //Async, by creating a new thread to prevent freezing
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() { public void run() {
            VoiceManager vm = VoiceManager.getInstance();
            Voice voice = vm.getVoice(VOICE_NAME);
            voice.allocate();
            voice.speak(word);
        } });

    }
}
