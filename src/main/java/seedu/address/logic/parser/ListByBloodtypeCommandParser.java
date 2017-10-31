package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListByBloodtypeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BloodtypeContainsKeywordPredicate;

//@@author Ernest
/**
 * Parses input arguments and creates a new ListByBloodtypeCommand object
 */
public class ListByBloodtypeCommandParser implements Parser<ListByBloodtypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListByBloodtypeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByBloodtypeCommand.MESSAGE_USAGE));
        }

        String[] bloodTypeKeyword = trimmedArgs.split("\\s+");

        return new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(Arrays.asList(bloodTypeKeyword)));

    }

}
