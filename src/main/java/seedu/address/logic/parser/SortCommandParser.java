//@@author majunting
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse user Input and create a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private final String[] attributes = {"name", "email", "phone", "address"};
    private final ArrayList<String> attributeList = new ArrayList<>(Arrays.asList(attributes));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException
     */
    public SortCommand parse(String argument) throws ParseException {
        String trimmedArg = argument.trim();
        if (attributeList.contains(trimmedArg)) {
            switch (trimmedArg) {
            case "phone": return new SortCommand(1);
            case "email": return new SortCommand(2);
            case "address": return new SortCommand(3);
            default: return new SortCommand(0);
            }
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        }
    }
}
