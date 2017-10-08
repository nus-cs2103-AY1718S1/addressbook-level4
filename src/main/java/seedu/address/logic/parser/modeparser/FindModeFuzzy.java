package seedu.address.logic.parser.modeparser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

/**
 * Finds contacts in fuzzy search.
 */
public class FindModeFuzzy extends CommandMode<FindCommand> {

    public FindModeFuzzy(String modeArgs) {
        super(modeArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidModeArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new FindCommand(new FuzzySearchPredicate(modeArgs));
    }
}
