package seedu.address.ui.autocompleter;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKING_NUMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

//@@author Kowalski985
/**
 * Parses text that the user has entered in the command box
 */
public class CommandBoxParser {

    protected static final int COMMAND_INDEX = 0;
    protected static final int ARGUMENT_INDEX = 1;

    private static final  String EMPTY_STRING = "";

    /**
     * Used for initial separation of command word and args
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Prefix[] ALL_PREFIXES = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
        PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    /**
     * Parses {@code commandBoxText} to see if it contains any instances of a {@code Command} and {@code Prefix}
     *
     * @return {@code String} array containing the {@code Command} at index 0 and the remaining arguments at index 1
     */
    public String[] parseCommandAndPrefixes(String commandBoxText) {
        requireNonNull(commandBoxText);
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

    /**
     * Returns the ArrayList of prefixes that are missing from the {@code argument}
     */
    public ArrayList<String> getMissingPrefixes(String argument) {
        requireNonNull(argument);
        Prefix[] prefixes = ALL_PREFIXES;
        ArrayList<String> missingPrefixes = new ArrayList<>();
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(argument, prefixes);
        Arrays.stream(ALL_PREFIXES)
                .filter(p -> isMissing(argMap, p))
                .forEach(p -> missingPrefixes.add(p.toString()));
        return missingPrefixes;
    }

    /**
     * Returns true if {@code prefix} is not present in the {@code argMap}
     * or if it is present but is still missing arguments
     */
    private boolean isMissing(ArgumentMultimap argMap, Prefix prefix) {
        return prefix.equals(PREFIX_TAG)
                || !argMap.getValue(prefix).isPresent()
                || argMap.getValue(prefix).get().equals(EMPTY_STRING);
    }

    private boolean isValidCommand(String commandWord) {
        return Arrays.asList(AutocompleteCommand.ALL_COMMANDS).contains(commandWord);
    }
}
