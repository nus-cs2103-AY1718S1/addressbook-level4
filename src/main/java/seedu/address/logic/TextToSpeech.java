package seedu.address.logic;
import javax.speech.*;
import javax.speech.synthesis.*;
import java.util.Locale;

public class TextToSpeech {
	public TextToSpeech (String message){
		try {
			// Create a synthesizer for English
			Synthesizer synth = Central.createSynthesizer(
				new SynthesizerModeDesc(Locale.ENGLISH));

			// Get it ready to speak
			synth.allocate();
			synth.resume();

			// Speak the "Hello world" string
			synth.speakPlainText(message, null);

			// Wait till speaking is done
			synth.waitEngineState(Synthesizer.QUEUE_EMPTY);

			// Clean up
			synth.deallocate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}