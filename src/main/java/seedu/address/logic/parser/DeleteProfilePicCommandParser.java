package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteProfilePicCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author soonwj
/**
 * Parses input arguments and creates a new DeleteProfilePicCommand object
 */
public class DeleteProfilePicCommandParser implements Parser<DeleteProfilePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProfilePicCommand
     * and returns an DeleteProfilePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProfilePicCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteProfilePicCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProfilePicCommand.MESSAGE_USAGE));
        }
    }
}
