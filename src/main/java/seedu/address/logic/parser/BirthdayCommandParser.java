package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

//@@author liliwei25
/**
 * Parses arguments and returns BirthdayCommand
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {

    private static final String SPACE = " ";
    private static final int INDEX_POS = 0;
    private static final int BIRTHDAY_POS = 1;
    private static final int CORRECT_LENGTH = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitArgs = args.trim().split(SPACE);

        Index index;
        Birthday birthday;
        try {
            index = ParserUtil.parseIndex(splitArgs[INDEX_POS]);
            birthday = getNewBirthday(splitArgs);
        } catch (IllegalValueException ive) {
            return getExceptionMessage(ive);
        }

        return new BirthdayCommand(index, birthday);
    }

    /**
     * Gets message from IllegalValueException
     *
     * @param ive IllegalValueException thrown from creating new Birthday
     * @throws ParseException With relevant message depending on error message from IllegalValueException
     */
    private BirthdayCommand getExceptionMessage(IllegalValueException ive) throws ParseException {
        if (ive.getMessage().equals(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE), ive);
        } else {
            throw new ParseException(ive.getMessage());
        }
    }

    /**
     * Checks length of arguments and creates new Birthday
     *
     * @param splitArgs Input provided by user
     * @return New Birthday if inputs are valid
     * @throws IllegalValueException If inputs are invalid
     */
    private Birthday getNewBirthday(String[] splitArgs) throws IllegalValueException {
        if (isIncorrectLength(splitArgs)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        return new Birthday(splitArgs[BIRTHDAY_POS]);
    }

    /**
     * Checks if the length of the arguments are correct
     *
     * @param splitArgs Input arguments by user
     * @return True if length is incorrect
     */
    private boolean isIncorrectLength(String[] splitArgs) {
        return splitArgs.length < CORRECT_LENGTH;
    }
}
