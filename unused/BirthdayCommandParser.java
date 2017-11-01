package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

//@@author LuLechuan
/**
 * Parses input arguments and creates a new BirthDayCommand object
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String birthdayInput = st.nextToken();
            String prefix = birthdayInput.substring(0, 2);

            if (!prefix.equals(PREFIX_BIRTHDAY.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
            }

            Birthday birthday = new Birthday(birthdayInput.substring(2));
            return new BirthdayCommand(index, birthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
        }
    }

}
