package seedu.address.logic.parser;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoceReminderCommand object
 */
public class RemoveCommandParser implements Parser<RemoveReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveReminderCommand
     * and returns an RemoveReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveReminderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE));
        }
    }

}
