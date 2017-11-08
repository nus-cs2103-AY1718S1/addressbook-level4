package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author JasmineSee

/**
 * Parses input arguments and creates a new DeletePhotoCommand object
 */
public class DeletePhotoCommandParser implements Parser<DeletePhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePhotoCommand
     * and returns an DeletePhotoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePhotoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePhotoCommand(index);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePhotoCommand.MESSAGE_USAGE));
        }
    }

}

