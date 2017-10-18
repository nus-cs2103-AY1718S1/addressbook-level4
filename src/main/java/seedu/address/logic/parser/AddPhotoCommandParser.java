package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPhotoCommand object
 */
public class AddPhotoCommandParser implements Parser<AddPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPhotoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);
            //String filepath
            return new AddPhotoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));


        }
    }
}
