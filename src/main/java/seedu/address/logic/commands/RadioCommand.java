package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.InternetConnectionCheck;
import seedu.address.logic.TextToSpeech;
import seedu.address.logic.threads.Radio;


//@@author hanselblack
/**
 * Plays Radio with radio play command
 * Stop Radio with radio stop command
 */
public class RadioCommand extends Command {

    public static final String COMMAND_WORD = "radio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/stop radio "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play or stop) "
            + "GENRE (must be either chinese, classic, news, pop) \n"
            + "Example: " + COMMAND_WORD + " play news ";

    public static final String[] GENRE_LIST = {"chinese", "classic", "news", "pop"};
    public static final String MESSAGE_NO_RADIO_PLAYING = "No radio is currently playing";
    public static final String MESSAGE_STOP = "Radio Stopped";
    public static final String MESSAGE_SUCCESS = "Radio Playing";
    public static final String MESSAGE_NO_INTERNET = "Not Connected to the Internet";

    private static Radio radio;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"chinese", "classic", "news", "pop"};

    public RadioCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public RadioCommand(String command) {
        this.command = command;
    }

    /**
     * Returns true if radio player is currently playing.
     * else return false
     */
    public static boolean isRadioPlaying() {
        if (radio == null) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Stops radio playing in the player
     */
    public static void stopRadioPlayer() {
        if (radio != null) {
            radio.stop();
        }
    }

    @Override
    public CommandResult execute() {

        boolean genreExist = Arrays.asList(GENRE_LIST).contains(genre);
        switch (command) {
        case "play":
            if (genreExist) {
                if (InternetConnectionCheck.isConnectedToInternet()) {
                    if (MusicCommand.isMusicPlaying()) {
                        MusicCommand.stopMusicPlayer();
                    }
                    stopRadioPlayer();
                    radio = new Radio(genre);
                    radio.start();

                    String printedSuccessMessage = genre.toUpperCase() + " " + MESSAGE_SUCCESS;
                    //Text to Speech
                    new TextToSpeech(printedSuccessMessage).speak();
                    return new CommandResult(printedSuccessMessage);
                } else {
                    return new CommandResult(MESSAGE_NO_INTERNET);
                }
            } else {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
            }
        case "stop":
            if (isRadioPlaying()) {
                radio.stop();
                //Text to Speech
                new TextToSpeech(MESSAGE_STOP).speak();
                return new CommandResult(MESSAGE_STOP);
            } else {
                //Text to Speech
                new TextToSpeech(MESSAGE_NO_RADIO_PLAYING).speak();
                return new CommandResult(MESSAGE_NO_RADIO_PLAYING);
            }
        default:
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RadioCommand // instanceof handles nulls
                && (this.genre == null || this.genre.equals(((RadioCommand) other).genre)) // state check
                && (this.command == null || this.command.equals(((RadioCommand) other).command))); // state check
    }
}


