package seedu.address.logic.parser.modeparser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.regex.Pattern;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

/**
 * Finds contacts by name.
 */
public class FindModeByName extends CommandMode<FindCommand> {

    public FindModeByName(String modeArgs) {
        super(modeArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidModeArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        String[] nameKeywords = modeArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    @Override
    boolean isValidModeArgs() {
        return super.isValidModeArgs()
             && !modeArgs.contains("-");
    }
}
