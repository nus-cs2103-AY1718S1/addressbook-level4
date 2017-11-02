//@@author arturs68
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePicCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePicCommand object
 */
public class ChangePicCommandParser implements Parser<ChangePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePicCommand
     * and returns an ChangePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePicCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String picturePath = argMultimap.getValue(PREFIX_PATH).orElse("");
            if (picturePath.equals("")) {
                throw new IllegalValueException("No path specified");
            }
            return new ChangePicCommand(index, picturePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePicCommand.MESSAGE_USAGE));
        }
    }
}
