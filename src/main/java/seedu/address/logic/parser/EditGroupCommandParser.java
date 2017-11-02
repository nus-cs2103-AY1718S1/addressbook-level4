//@@author hthjthtrh
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    private static final Set<String> validOp = new HashSet<>(Arrays.asList("grpName", "add", "delete"));

    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();

        List<String> argsList = Arrays.asList(userInput.split(" "));

        if (argsList.size() != 3 || userInput.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        String grpName;
        String operation;
        String detail;

        // parseing
        try {
            grpName = argsList.get(0);
            operation = argsList.get(1);

            if (isInteger(grpName) || isInteger(operation)) {
                throw new Exception();
            }
            if (!validOp.contains(operation)) {
                throw new Exception();
            }

            detail = argsList.get(2);
            // if operation is add or delete, detail should be an index
            if (operation.equals("add") || operation.equals("delete")) {
                if (!isInteger(detail)) {
                    throw new Exception();
                }
            } else {
                // operation is to change name, need to enforce the rule that group name is not an integer
                if (isInteger(detail)) {
                    throw new Exception();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        return new EditGroupCommand(grpName, operation, detail);
    }

    /**
     * trues to parse input into an integer
     * @param input
     * @return true if input is integer
     */
    private boolean isInteger(String input) {
        boolean isInt;
        try {
            ParserUtil.parseInt(input);
            isInt = true;
        } catch (IllegalValueException e) {
            isInt = false;
        }
        return isInt;
    }
}
//@@author
