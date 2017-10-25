package seedu.address.ui;


import java.util.regex.Pattern;

/**
 * Parses text that the user has entered in the command box
 */
public class CommandBoxParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public CommandBoxParser() { };
}
