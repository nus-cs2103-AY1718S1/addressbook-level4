package seedu.address.logic.parser.optionparser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

/**
 * Finds contacts by name.
 */
public class FindOptionByName extends CommandOption<FindCommand> {

    public FindOptionByName(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        String[] nameKeywords = optionArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    @Override
    boolean isValidOptionArgs() {
        return super.isValidOptionArgs()
             && !optionArgs.contains("-");
    }
}
