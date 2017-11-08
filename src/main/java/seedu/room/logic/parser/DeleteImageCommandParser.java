package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.core.index.Index;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.DeleteImageCommand;
import seedu.room.logic.parser.exceptions.ParseException;

//@@author shitian007
/**
 * Parses the given {@code String} of arguments in the context of the DeleteImageCommand
 * and returns an DeleteImageCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DeleteImageCommandParser implements Parser<DeleteImageCommand> {
    @Override
    public DeleteImageCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteImageCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteImageCommand.MESSAGE_USAGE));
        }
    }
}
