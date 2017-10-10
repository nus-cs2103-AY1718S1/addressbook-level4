package seedu.address.logic.parser.optionparser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

/**
 * Finds contacts in fuzzy search.
 */
public class FindOptionFuzzy extends CommandOption<FindCommand> {

    public FindOptionFuzzy(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new FindCommand(new FuzzySearchPredicate(optionArgs));
    }
}
