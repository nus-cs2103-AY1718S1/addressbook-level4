package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddBirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

//@@author jacoblipech
/**
 * Parses input arguments and creates a new AddBirthdayCommand object
 */
public class AddBirthdayCommandParser implements Parser<AddBirthdayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddBirthdayCommand
     * and returns a AddBirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BIRTHDAY);

        if (!isPrefixPresent(argMultimap, PREFIX_BIRTHDAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBirthdayCommand.MESSAGE_USAGE));
        }

        try {
            String enteredIndex = argMultimap.getPreamble();
            Index index = ParserUtil.parseIndex(enteredIndex);

            String birthdayName = argMultimap.getValue(PREFIX_BIRTHDAY).orElse("");
            Birthday toAdd = new Birthday(birthdayName);

            return new AddBirthdayCommand(index, toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the prefix does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
