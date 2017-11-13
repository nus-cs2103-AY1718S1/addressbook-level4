package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.InstaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InstaCommand object
 */

//@@author hengyu95
public class InstaCommandParser implements Parser<InstaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InstaCommand
     * and returns an InstaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InstaCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new InstaCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));
        }
    }
}
