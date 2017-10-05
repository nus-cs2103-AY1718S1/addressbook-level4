package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteByNameCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns an DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {
        try {
            String name = ParserUtil.parseNameString(args);
            return new DeleteByNameCommand(name);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }
    }

}
