package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Adoby7
/**
 * Parses input arguments and creates a new DisplayCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {
    public static final int INDEX_POSITION = 0;
    public static final int PARTICULAR_POSITION = 1;
    public static final int REQUIRED_LENGTH = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns an DisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split(" ");
            if (splitArgs.length < REQUIRED_LENGTH) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(splitArgs[INDEX_POSITION]);
            String particular = splitArgs[PARTICULAR_POSITION].trim();
            return new DisplayCommand(index, particular);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
        }
    }
}
