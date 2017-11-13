package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Favourite;

//@@author justintkj
/**
 * Parses arguments and returns FavouriteCommand
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Favourite favourite;
        try {
            index = ParserUtil.parseIndex(args);
            favourite = new Favourite(Favourite.COLOR_SWITCH);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE), ive);
        }

        return new FavouriteCommand(index, favourite);
    }
}
