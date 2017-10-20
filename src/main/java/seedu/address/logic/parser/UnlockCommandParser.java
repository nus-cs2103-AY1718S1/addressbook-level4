package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityUtil;

/**
 * Parses input arguments and creates a new UnlockCommand object
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code args} of arguments in the context of the UnlockCommand
     * and returns an UnlockCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnlockCommand parse(String userInput) throws ParseException {
        if (!SecurityUtil.isValidPassword(userInput)) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new UnlockCommand(userInput);
    }
}
