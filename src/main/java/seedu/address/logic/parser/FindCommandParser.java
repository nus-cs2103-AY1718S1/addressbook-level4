package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_TAG_FULL);

        if (argMultimap.getValue(PREFIX_TASK).isPresent() && argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            return new FindTaskCommandParser().parse(args);
        } else if (argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            String filteredArgs = args.replace(PREFIX_TAG_FULL.getPrefix(), " ");
            return new FindTagCommandParser().parse(filteredArgs);
        } else {
            return new FindPersonCommandParser().parse(args);
        }
    }
}
