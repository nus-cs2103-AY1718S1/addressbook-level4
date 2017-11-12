package seedu.address.ui;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author Kowalski985
/**
 * Parses text that the user has entered in the command box
 */
public class CommandBoxParser {

    protected static final int COMMAND_INDEX = 0;
    protected static final int ARGUMENT_INDEX = 1;

    private static final  String EMPTY_STRING = "";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses {@code String} to see if it contains any instances of a {@code Command} and {@code Prefix}
     * @param commandBoxText
     */
    public String[] parseCommandAndPrefixes(String commandBoxText) {
        String[] parseResults = {EMPTY_STRING, EMPTY_STRING };
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandBoxText.trim());
        if (!matcher.matches()) {
            return parseResults;
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if (isValidCommand(commandWord)) {
            parseResults[COMMAND_INDEX] = commandWord;
        }
        parseResults[ARGUMENT_INDEX] = arguments;
        return parseResults;
    }

    private boolean isValidCommand(String commandWord) {
        return Arrays.asList(AutocompleteCommand.ALL_COMMANDS).contains(commandWord);
    }
}
