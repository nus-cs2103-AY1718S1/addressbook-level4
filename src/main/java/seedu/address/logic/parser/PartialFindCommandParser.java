package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.PartialFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsPartialKeywordsPredicate;

//@@author RSJunior37
/**
 * Parses input arguments and creates a new PartialFindCommand object
 */
public class PartialFindCommandParser implements Parser<PartialFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PartialFindCommand
     * and returns an PartialFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PartialFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PartialFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new PartialFindCommand(new NameContainsPartialKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
