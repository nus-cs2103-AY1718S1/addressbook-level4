//@@author zengfengw
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

/**
 * Birthday Command Parser
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {
    /**
     * Parse the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BIRTHDAY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
        }

        String birthday = argMultimap.getValue(PREFIX_BIRTHDAY).orElse("");

        try {
            Birthday temp = new Birthday(birthday);
            return new BirthdayCommand(index, temp);
        } catch (IllegalValueException ive) {
            throw new ParseException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }

    }
}
