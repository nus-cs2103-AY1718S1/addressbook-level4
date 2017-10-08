package seedu.address.logic;

import java.io.File;
import java.io.FileOutputStream;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Text To Speech
 */
public class TextToSpeech {

    private static MediaPlayer mediaPlayer;

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
        VoiceProvider tts = new VoiceProvider("c580b327590b4cb881deee28bf85a543");
        VoiceParameters params = new VoiceParameters(word, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.MP3);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
            @Override
            public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                System.out.print(e.getException().getMessage());
            }
        });

        tts.addSpeechDataEventListener(new SpeechDataEventListener() {
            @Override
            public void handleSpeechDataEvent(SpeechDataEvent e) {
                try {
                    byte[] voice = (byte[]) e.getData();

                    FileOutputStream fos = new FileOutputStream("audio/voice.mp3");
                    fos.write(voice, 0, voice.length);
                    fos.flush();
                    fos.close();
                    String musicFile = "audio/voice.mp3";
                    if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                        mediaPlayer.stop();
                    }
                    Media sound = new Media(new File(musicFile).toURI().toString());
                    mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.setVolume(100.0);
                    mediaPlayer.play();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        tts.speechAsync(params);
    }
}
