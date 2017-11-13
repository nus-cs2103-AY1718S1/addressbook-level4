package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jhchia7

/**
 * Parses input arguments and creates a new SendCommand object
 */
public class SendCommandParser implements Parser<SendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SendCommand
     * and returns an SendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SendCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendCommand.MESSAGE_USAGE));
        }
    }
}
