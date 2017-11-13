package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * A utility class for Reminder.
 */
public class ReminderUtil {

    /**
     * Returns an add command string for adding the {@code reminder}.
     */
    public static String getAddReminderCommand(ReadOnlyReminder reminder) {
        return AddReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(ReadOnlyReminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TASK + reminder.getTask().taskName + " ");
        sb.append(PREFIX_DATE + reminder.getDate().date + " ");
        sb.append(PREFIX_PRIORITY + reminder.getPriority().value + " ");
        sb.append(PREFIX_MESSAGE + reminder.getMessage().message + " ");
        reminder.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
