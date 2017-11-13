//@@author duyson98

package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RetrieveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new RetrieveCommand object
 */
public class RetrieveCommandParser implements Parser<RetrieveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RetrieveCommand
     * and returns an RetrieveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RetrieveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(RetrieveCommand.MESSAGE_EMPTY_ARGS, RetrieveCommand.MESSAGE_USAGE));
        }
        try {
            return new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(trimmedArgs)));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

    }

}
