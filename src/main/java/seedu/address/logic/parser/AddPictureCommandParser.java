package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPictureCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new AddPictureCommand object
 */
public class AddPictureCommandParser implements Parser<AddPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPictureCommand
     * and returns an AddPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPictureCommand parse(String args) throws ParseException, CommandException {
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
