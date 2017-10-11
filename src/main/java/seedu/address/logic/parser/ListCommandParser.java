package seedu.address.logic.parser;

import static seedu.address.logic.parser.SortUtil.setupArguments;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand(null);
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<SortArgument> sortArgumentList = new ArrayList<>();

        setupArguments(keywords, new ArrayList<>(), sortArgumentList);

        return new ListCommand(sortArgumentList);
    }

}
