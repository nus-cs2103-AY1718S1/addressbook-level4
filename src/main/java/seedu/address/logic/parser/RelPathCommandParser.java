package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RelPathCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Xenonym
/**
 * Parses input arguments and creates a new RelPathCommand object.
 */
public class RelPathCommandParser implements Parser<RelPathCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of RelPathCommand
     * and returns an RelPathCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public RelPathCommand parse(String userInput) throws ParseException {
        String[] args = userInput.trim().split(" ");
        if (args.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelPathCommand.MESSAGE_USAGE));
        }

        try {
            return new RelPathCommand(ParserUtil.parseIndex(args[0]), ParserUtil.parseIndex(args[1]));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelPathCommand.MESSAGE_USAGE));
        }
    }
}
