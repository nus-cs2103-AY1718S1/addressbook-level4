//@@author hthjthtrh
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewGroupCommand object
 */
public class ViewGroupCommandParser implements Parser<ViewGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        List<String> argsList = Arrays.asList(userInput.split("\\s+"));

        if (argsList.size() > 1 || argsList.get(0).equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE);
        }

        try {
            int index = Integer.parseInt(argsList.get(0));
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE);
            }
            return new ViewGroupCommand(Index.fromOneBased(index));
        } catch (NumberFormatException e) {
            // argument is not an index
            return new ViewGroupCommand(userInput);
        }
    }
}
//@@author
