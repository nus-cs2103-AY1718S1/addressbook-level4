package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetColourCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new SetColourCommand object.
 */
public class SetColourCommandParser implements Parser<SetColourCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetColourCommand
     * and returns an SetColourCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetColourCommand parse(String args) throws ParseException {
        try {
            String[] setColourArgs = ParserUtil.parseColourCommand(args);
            return new SetColourCommand(setColourArgs[0], setColourArgs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetColourCommand.MESSAGE_USAGE));
        }
    }
}
