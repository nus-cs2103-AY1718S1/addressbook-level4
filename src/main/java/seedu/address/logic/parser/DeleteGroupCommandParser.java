//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments in the context of DeleteGroupCommand returns the command
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        List<String> argList = Arrays.asList(userInput.split(" "));

        if (userInput.equals("") || argList.size() != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        }

        try {
            ParserUtil.parseInt(argList.get(0));
        } catch (IllegalValueException e) {
            // non-integer, a possible valid group name
            return new DeleteGroupCommand(argList.get(0));
        }

        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
    }
}
//@@author
