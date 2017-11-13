package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author charlesgoh
/**
 * Parses input arguments and returns a new LockCommand object
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LockCommand
     * and returns a LockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);
        String trimmedArgs = args.trim();

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
            }
            String[] passwordKeywords = trimmedArgs.split("\\s+");
            return new LockCommand(passwordKeywords[0]);
        } else {
            String[] passwordKeywords = trimmedArgs.split(PREFIX_PASSWORD.getPrefix());
            return new LockCommand(passwordKeywords[1]);
        }
    }
}
