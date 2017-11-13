//@@author hthjthtrh
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    private static final Set<String> validOp = new HashSet<>(Arrays.asList("gn", "add", "delete"));
    private String grpName;
    private Index grpIndex;
    private String operation;
    private String newName;
    private Index personIndex;
    private boolean indicateByIndex;


    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        List<String> argsList = Arrays.asList(userInput.split("\\s+"));

        if (argsList.size() != 3 || userInput.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        parseGroupIndicator(argsList.get(0));
        parseOpIndicator(argsList.get(1));
        if ("gn".equals(operation)) {
            parseNewName(argsList.get(2));
            return new EditGroupCommand(grpName, grpIndex, operation, newName, indicateByIndex);
        } else {
            parseIndex(argsList.get(2));
            return new EditGroupCommand(grpName, grpIndex, operation, personIndex, indicateByIndex);
        }
    }

    /**
     * parses the indicator to either a group name or index
     * @param grpIndicator
     * @throws ParseException
     */
    private void parseGroupIndicator(String grpIndicator) throws ParseException {
        try {
            int index = Integer.parseInt(grpIndicator);
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
            }
            grpIndex = Index.fromOneBased(index);
            indicateByIndex = true;
        } catch (NumberFormatException e) {
            // non-integer, must be a group name
            grpName = grpIndicator;
            indicateByIndex = false;
        }
    }

    /**
     * parses the operation indicator to existing operations
     * @param opIndicator
     * @throws ParseException
     */
    private void parseOpIndicator(String opIndicator) throws ParseException {
        if (!validOp.contains(opIndicator)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } else {
            operation = opIndicator;
        }
    }

    /**
     * parses the new group name
     * @param groupName
     * @throws ParseException if new name is an integer
     */
    private void parseNewName(String groupName) throws ParseException {
        try {
            Integer.parseInt(groupName);
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } catch (NumberFormatException e) {
            this.newName = groupName;
        }
    }

    /**
     * parses the index string into an Index object
     * @param index
     * @throws ParseException if index is of invalid value
     */
    private void parseIndex(String index) throws ParseException {
        try {
            personIndex = ParserUtil.parseIndex(index);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }
    }
}
//@@author
