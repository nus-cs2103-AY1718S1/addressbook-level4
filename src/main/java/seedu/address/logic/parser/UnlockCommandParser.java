package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author charlesgoh
/**
 * Parses input arguments and returns a new LockCommand object
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Password is required";

    /**
     * Parses the given {@code String} of arguments in the context of the UnlockCommand
     * and returns a UnlockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);
        String trimmedArgs = args.trim();

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
            }
            String[] passwordKeywords = trimmedArgs.split("\\s+");
            return new UnlockCommand(passwordKeywords[0]);
        } else {
            String[] passwordKeywords = trimmedArgs.split(PREFIX_PASSWORD.getPrefix());
            return new UnlockCommand(passwordKeywords[1]);
        }
    }
}
