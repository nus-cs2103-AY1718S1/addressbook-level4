package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String DATA_FIELD_NAME = "name";
    public static final String DATA_FIELD_PHONE = "phone";
    public static final String DATA_FIELD_EMAIL = "email";
    public static final String DATA_FIELD_ADDRESS = "address";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String dataFieldToSortByFirst = trimmedArgs.toLowerCase();

        switch (dataFieldToSortByFirst) {
        case DATA_FIELD_NAME:
        case DATA_FIELD_PHONE:
        case DATA_FIELD_EMAIL:
        case DATA_FIELD_ADDRESS:
            return new SortCommand(dataFieldToSortByFirst);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

    }

}

