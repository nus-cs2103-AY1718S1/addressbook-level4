package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author dalessr
/**
 * Parses input arguments and creates a new BirthdayRemoveCommand object
 */
public class BirthdayRemoveCommandParser implements Parser<BirthdayRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayRemoveCommand
     * and returns an BirthdayRemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new BirthdayRemoveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayRemoveCommand.MESSAGE_USAGE));
        }
    }
}
