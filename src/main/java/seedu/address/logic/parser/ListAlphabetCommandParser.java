//@@author majunting
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListAlphabetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;


/**
 * Parses input argument and creates a new ListAlphabetCommand object
 */
public class ListAlphabetCommandParser implements Parser<ListAlphabetCommand> {

    /**
     * Parses given {@code String} of argument in the context of the ListAlphabetCommand
     * and returns a ListAlphabetCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAlphabetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ListAlphabetCommand.MESSAGE_USAGE);
        }

        String keyword = trimmedArgs;

        return new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(keyword));
    }
}
