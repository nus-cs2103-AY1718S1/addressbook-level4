//@@author quangtdn
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteListCommand object
 */
public class DeleteListCommandParser implements Parser<DeleteListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteListCommand
     * and returns an DeleteListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteListCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteListCommand.MESSAGE_USAGE));
            }
            String[] indices = trimmedArgs.split("\\s+");
            List<String> inputInString = Arrays.asList(indices);

            List<Index> input = new ArrayList<Index>();

            for (String ind: inputInString) {
                Index index = ParserUtil.parseIndex(ind);
                input.add(index);
            }

            return new DeleteListCommand(input);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteListCommand.MESSAGE_USAGE));
        }
    }
}
//@@author