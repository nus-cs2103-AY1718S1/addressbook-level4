package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddPictureCommandParser {

    public AddPictureCommand parse(String args) throws ParseException {
        try {
            if (args.trim().equals("")) {
                return new AddPictureCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new AddPictureCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPictureCommand.MESSAGE_USAGE));
        }
    }
}
