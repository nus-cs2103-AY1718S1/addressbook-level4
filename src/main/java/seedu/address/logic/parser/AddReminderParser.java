package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
//@@author RonakLakhotia
/**
 * Parses the input arguments and creates a new AddReminderCommand object.
 */
public class AddReminderParser implements Parser<AddReminder> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminder
     * and returns an AddReminder object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminder parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                        PREFIX_REMINDER_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                PREFIX_REMINDER_PRIORITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));
        }

        try {
            ReminderDetails details = ParserUtil.parseDetails(argMultimap.getValue(PREFIX_REMINDER_DETAILS)).get();
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_REMINDER_PRIORITY)).get();
            DueDate dueDate = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_REMINDER_DUE_DATE)).get();
            ReadOnlyReminder reminder = new Reminder(details, priority , dueDate);

            return new AddReminder(reminder);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
