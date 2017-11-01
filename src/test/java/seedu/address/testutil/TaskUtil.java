package seedu.address.testutil;

import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd HH:mm");

    /**
     * Returns an add task command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getHeader().header + " ");
        if (task.getStartDateTime().isPresent()) {
            sb.append("from ");
            sb.append(task.getStartDateTime().get().format(FORMATTER));
            sb.append("to ");
            sb.append(task.getEndDateTime().get().format(FORMATTER));
        } else if ((!task.getStartDateTime().isPresent()) && (task.getEndDateTime().isPresent())) {
            sb.append("by ");
            sb.append(task.getEndDateTime().get().format(FORMATTER));
        }
        return sb.toString();
    }
}
