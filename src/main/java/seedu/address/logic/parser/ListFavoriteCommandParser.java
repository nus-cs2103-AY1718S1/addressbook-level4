//@@author hxy0229
package seedu.address.logic.parser;

import seedu.address.logic.commands.ListFavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FavoritePredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class ListFavoriteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ListFavoriteCommand parse() throws ParseException {
        return new ListFavoriteCommand(new FavoritePredicate());
    }
}
//@@author
