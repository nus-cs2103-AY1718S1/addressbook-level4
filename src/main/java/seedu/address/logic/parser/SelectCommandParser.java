package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK);
        if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            String trimmed = args.replaceFirst(PREFIX_TASK.getPrefix(), "").trim();
            return new SelectTaskCommandParser().parse(trimmed);
        } else {
            return new SelectPersonCommandParser().parse(args);
        }
    }
}
