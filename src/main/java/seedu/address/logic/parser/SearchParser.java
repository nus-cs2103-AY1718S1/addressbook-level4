package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NamePhoneTagContainsKeywordsPredicate;

//@@author willxujun
/**
 * Represents a parser that parses input from the search bar
 */
public class SearchParser implements Parser<Command> {

    /**
     * returns a Command as parsed
     * @param args
     * @return a SearchCommand of the search word args if search bar input is not empty, a ListCommand if empty
     * @throws ParseException that should never be thrown because there is no restriction on search keywords
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchCommand(new NamePhoneTagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
