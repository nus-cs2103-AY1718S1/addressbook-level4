package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.Radio;
import seedu.address.logic.TextToSpeech;

//@@author hanselblack
/**
 * Plays Radio with radio play command
 * Stop Radio with radio stop command
 */
public class RadioCommand extends Command {

    public static final String COMMAND_WORD = "radio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/stop radio "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play, pause or stop) "
            + "GENRE (must be either chinese, classic, comedy, country, news, pop) \n"
            + "Example: " + COMMAND_WORD + " play news ";

    public static final String MESSAGE_STOP = "Radio Stopped";

    public static final String MESSAGE_SUCCESS = "Radio Playing";

    private static Radio music;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"chinese", "classic", "comedy", "country", "news", "pop"};

    public RadioCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public RadioCommand(String command) {
        this.command = command;
    }

    /**
     * Stops radio playing in the player
     */
    public static void stopRadioPlayer() {
        if (music != null) {
            music.stop();
        }
    }

    @Override
    public CommandResult execute() {

        boolean genreExist = Arrays.asList(genreList).contains(genre);
        switch (command) {
        case "play":
            if (MusicCommand.isMusicPlaying()) {
                MusicCommand.stopMusicPlayer();
            }
            stopRadioPlayer();
            if (genreExist) {
                music = new Radio(genre);
                music.start();

                String printedSuccessMessage = genre.toUpperCase() + " " + MESSAGE_SUCCESS;
                //Text to Speech
                new TextToSpeech(printedSuccessMessage);
                return new CommandResult(printedSuccessMessage);
            }
            return new CommandResult(MESSAGE_USAGE);
        case "stop":
            music.stop();
            //Text to Speech
            new TextToSpeech(MESSAGE_STOP);
            return new CommandResult(MESSAGE_STOP);
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


