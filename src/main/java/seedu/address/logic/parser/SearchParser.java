package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Represents a parser that parses input from the search bar
 */
public class SearchParser {

    /**
     * returns a Command as parsed
     * @param args
     * @return a FindCommand of the search word args if search bar input is not empty, a ListCommand if empty search bar
     * @throws ParseException
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
