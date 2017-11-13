package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListGroupCommand;
import seedu.address.logic.commands.ListTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kengying
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equalsIgnoreCase("all")) {
            return new ListCommand();
        } else if (trimmedArgs.equalsIgnoreCase("tags")) {
            return new ListTagCommand();
        } else if (trimmedArgs.equalsIgnoreCase("groups")) {
            return new ListGroupCommand();
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE,
                            ListTagCommand.MESSAGE_USAGE, ListGroupCommand.MESSAGE_SUCCESS));
        }

    }

}
