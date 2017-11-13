# hanselblack
###### \SpeechToTextCommand.java
``` java
/**
 * Spech to text, using sphinx 4
 */
public class SpeechToTextCommand extends Command{

    public static final String COMMAND_WORD = "speak";

    public static final String MESSAGE_SUCCESS = "speaking...";

    private static MediaPlayer mediaPlayer;

    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_SUCCESS);
    }


    public static void main(String[] args) throws Exception {

        Logger cmRootLogger = Logger.getLogger("default.config");
        cmRootLogger.setLevel(java.util.logging.Level.OFF);
        String conFile = System.getProperty("java.util.logging.config.file");
        if (conFile == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        // Pause recognition process. It can be resumed then with startRecognition(false).

        while (true) {
            String utterance = recognizer.getResult().getHypothesis();
            if (utterance.equals("one zero one")
                    || utterance.equals("one oh one"))
                break;
            else
                System.out.println(utterance);
        }
        recognizer.stopRecognition();
    }
}
```
###### \TextToSpeech.java
``` java
/**
 * Text To Speech, Reads in String of Text, downloads an .Mp3 file using Webservices then proceeds to play that .Mp3 file
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
```
###### \TextToSpeechParser.java
``` java
/**
 * Parses input arguments and creates a new TextToSpeech object
 */
public class TextToSpeechParser {

    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an TextToSpeech object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TextToSpeech parse(String args) throws ParseException {
        return new TextToSpeech(args);
    }
}
```
