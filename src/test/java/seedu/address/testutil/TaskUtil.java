package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A Utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getDescription().taskDescription + " ");
        sb.append(PREFIX_START_DATE + task.getStartDate().date.toString() + " ");
        sb.append(PREFIX_DEADLINE + task.getDeadline().date.toString() + " ");
        return sb.toString();
    }
}
