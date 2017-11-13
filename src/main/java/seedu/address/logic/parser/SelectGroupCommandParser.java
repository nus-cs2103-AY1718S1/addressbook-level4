package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectGroupCommand object
 */
public class SelectGroupCommandParser implements Parser<SelectGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectGroupCommand
     * and returns an SelectGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectGroupCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectGroupCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectGroupCommand.MESSAGE_USAGE));
        }
    }
}
