//@@author qihao27
package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteByNameCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAltCommand
     * and returns an DeleteAltCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new DeleteByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
