//@@author siri99
package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, new Prefix(""));

        String sortFilter;
        sortFilter = argMultimap.getPreamble();

        if (sortFilter.equals("sort")) {
            //No arguments, set to default filter "name" which sorts in alphabhetical order
            sortFilter = "";
        }

        if (!(sortFilter.equals("name") || sortFilter.equals("birthday") || sortFilter.equals("b")
                || sortFilter.equals("") || sortFilter.equals("score") || sortFilter.equals("s"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(sortFilter);
    }
}
//@@author siri99

