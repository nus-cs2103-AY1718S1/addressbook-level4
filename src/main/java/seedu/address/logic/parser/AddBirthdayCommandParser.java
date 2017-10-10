package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddBirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

/**
 * Parses input arguments and creates a new AddBirthdayCommand object
 */
public class AddBirthdayCommandParser implements Parser<AddBirthdayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBirthdayCommand
     * and returns an AddBirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBirthdayCommand parse(String args) throws ParseException {
        try {
            String[] arr = args.split(" ");
            System.out.println(Arrays.toString(arr));
            Index index = ParserUtil.parseIndex(arr[1]);
            Birthday birthday = ParserUtil.parseBirthday(arr[2]);
            return new AddBirthdayCommand(index, birthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBirthdayCommand.MESSAGE_USAGE));
        }
    }

}
