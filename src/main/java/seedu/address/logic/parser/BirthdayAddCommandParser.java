package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

/**
 * Parses input arguments and creates a new BirthdayAddCommand object
 */
public class BirthdayAddCommandParser implements Parser<BirthdayAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayAddCommand
     * and returns an BirthdayAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] parameters = trimmedArgs.split(" ");
        if (parameters.length <= 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayAddCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(parameters[0]);
            Birthday birthday = new Birthday(parameters[parameters.length - 1]);
            return new BirthdayAddCommand(index, birthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayAddCommand.MESSAGE_USAGE));
        }
    }
}
