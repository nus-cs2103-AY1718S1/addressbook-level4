package seedu.address.ui;


import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKING_NUMBER;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

import java.util.ArrayList;
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
    private static final  String SPACE = " ";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Prefix[] ALL_PREFIXES = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
            PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

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

    /**
     * Returns the ArrayList of prefixes that are missing from the {@code String argument}
     * @return {@code ArrayList} of missing prefixes as {@code Strings}
     */
    public ArrayList<String> getMissingPrefixes(String argument) {
        ArrayList<String> missingPrefixes = new ArrayList<>();
        Arrays.stream(ALL_PREFIXES)
                .filter(p -> isMissing(p, argument))
                .forEach(p -> missingPrefixes.add(p.toString()));
        return missingPrefixes;
    }

    private boolean isMissing(Prefix prefix, String argument) {
        System.out.println(prefix.toString());
        return !argument.contains(SPACE + prefix.toString());
    }

    private boolean isValidCommand(String commandWord) {
        return Arrays.asList(AutocompleteCommand.ALL_COMMANDS).contains(commandWord);
    }
}
