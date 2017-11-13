package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminders.ReadOnlyReminder;

//@@author justinpoh
/**
 * A utility class for Reminder.
 */
public class ReminderUtil {

    /**
     * Returns an add reminder command string for adding the {@code reminder}.
     */
    public static String getAddReminderCommand(ReadOnlyReminder reminder) {
        return AddReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(ReadOnlyReminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER + reminder.getReminder() + " ");
        sb.append(PREFIX_DATE + reminder.getDate().value + " ");
        sb.append(PREFIX_TIME + reminder.getTime().value + " ");
        return sb.toString();
    }
}
