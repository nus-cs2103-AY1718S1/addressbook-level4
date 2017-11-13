//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments in the context of DeleteGroupCommand returns the command
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        List<String> argList = Arrays.asList(userInput.split("\\s+"));

        if (userInput.equals("") || argList.size() != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        }

        try {
            int index = Integer.parseInt(argList.get(0));
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
            }
            return new DeleteGroupCommand(Index.fromOneBased(index), true);
        } catch (NumberFormatException e) {
            // non-integer
            return new DeleteGroupCommand(argList.get(0), false);
        }

    }
}
//@@author
