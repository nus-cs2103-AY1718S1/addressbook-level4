package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import seedu.address.logic.commands.AddReminder;
import seedu.address.model.reminder.ReadOnlyReminder;
//@@author RonakLakhotia
/**
 * A utility class for Reminder
 */
public class ReminderUtil {

    /**
     * Returns an add command string for adding the {@code reminder}.
     */
    public static String getAddCommand(ReadOnlyReminder reminder) {
        return AddReminder.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(ReadOnlyReminder reminder) {

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER_DETAILS + reminder.getDetails().details + " ");
        sb.append(PREFIX_REMINDER_PRIORITY + reminder.getPriority().priority + " ");
        sb.append(PREFIX_REMINDER_DUE_DATE + reminder.getDueDate().date + " ");
        return sb.toString();
    }
}
