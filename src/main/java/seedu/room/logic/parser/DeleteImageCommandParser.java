package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.core.index.Index;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.DeleteImageCommand;
import seedu.room.logic.parser.exceptions.ParseException;

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
