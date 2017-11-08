package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeletePictureCommandParser implements Parser<DeletePictureCommand> {

    public DeletePictureCommand parse(String args) throws ParseException {
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
