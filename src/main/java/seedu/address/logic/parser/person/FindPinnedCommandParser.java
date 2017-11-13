package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.person.FindPinnedCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasKeywordsPredicate;

//@@author Alim95
/**
 * Parses input arguments and creates a new FindPinnedCommand object
 */
public class FindPinnedCommandParser implements Parser<FindPinnedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPinnedCommand
     * and returns an FindPinnedCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPinnedCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPinnedCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPinnedCommand(new PersonHasKeywordsPredicate(Arrays.asList(nameKeywords), true));
    }

    @Override
    public String getCommandWord() {
        return FindPinnedCommand.COMMAND_WORD;
    }
}
