package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePictureCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new {@code DeletePictureCommand} object
 */
public class DeletePictureCommandParser implements Parser<DeletePictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePictureCommand
     * and returns an DeletePictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePictureCommand parse(String args) throws ParseException, CommandException {
        try {
            if (args.trim().equals("")) {
                return new DeletePictureCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new DeletePictureCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePictureCommand.MESSAGE_USAGE));
        }
    }
}
