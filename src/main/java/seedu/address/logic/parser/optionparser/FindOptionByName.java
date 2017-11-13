//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;
import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
    public boolean isValidOptionArgs() {
        return !optionArgs.isEmpty() && !optionArgs.contains(PREFIX_OPTION_INDICATOR);
    }
}
