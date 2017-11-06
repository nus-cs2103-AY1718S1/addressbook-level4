package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeProfilePictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for ChangeProfilePictureCommand
 */
public class ChangeProfilePictureCommandParser implements Parser<ChangeProfilePictureCommand> {

    /**
     * Parse arguments given by AddressBookParser to change profile picture
     * @param args
     * @return
     * @throws ParseException
     */
    public ChangeProfilePictureCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parameters = trimmedArgs.split("\\s+");

        if (trimmedArgs.isEmpty() || parameters.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(parameters[0]);
            String picturePath = parameters[1];
            return new ChangeProfilePictureCommand(index, picturePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));
        }
    }
}
