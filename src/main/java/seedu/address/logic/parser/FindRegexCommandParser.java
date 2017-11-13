// @@author donjar

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REGEX;

import java.util.regex.PatternSyntaxException;

import seedu.address.logic.commands.FindRegexCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesRegexPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindRegexCommandParser implements Parser<FindRegexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRegexCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        try {
            return new FindRegexCommand(new NameMatchesRegexPredicate(trimmed));
        } catch (PatternSyntaxException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
        }
    }

}
