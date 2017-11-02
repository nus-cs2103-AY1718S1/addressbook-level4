package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.locateMrtCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new locateMrtCommand object
 */
public class LocateMrtCommandParser implements Parser<locateMrtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the locateMrtCommand
     * and returns an locateMrtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public locateMrtCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new locateMrtCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, locateMrtCommand.MESSAGE_USAGE));
        }
    }

}
