package seedu.address.logic.parser;

import seedu.address.logic.commands.FindFavCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FavouritePredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindFavCommandParser implements Parser<FindFavCommand> {

    /**
     *  parses the given {@code String} of arguments in the context of the FindTagCommand
     *  and returns an FindTagCommand object for execution.
     *  @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public FindFavCommand parse(String a) throws ParseException {
        return new FindFavCommand(new FavouritePredicate());
    }
}
