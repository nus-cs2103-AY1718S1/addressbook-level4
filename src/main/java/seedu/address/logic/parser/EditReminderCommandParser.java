package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author justinpoh
/**
 * Parses input arguments and creates a new EditReminderCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER, PREFIX_DATE, PREFIX_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
        }

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        Optional<String> optionalReminder = argMultimap.getValue(PREFIX_REMINDER);
        String reminder;
        if (optionalReminder.isPresent()) {
            reminder = optionalReminder.get();
            if (reminder.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditReminderCommand.MESSAGE_REMINDER_FORMAT));
            }
            editReminderDescriptor.setReminder(reminder);
        }

        try {
            ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).ifPresent(editReminderDescriptor::setDate);
            ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)).ifPresent(editReminderDescriptor::setTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }
}
