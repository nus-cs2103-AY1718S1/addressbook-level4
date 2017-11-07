package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

//@@author zhangshuoyang
/**
 * A utility class for Task.
 */
public class TaskUtil {

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
        sb.append(task.getDescription().descriptionName + " ");
        sb.append(PREFIX_PRIORITY + task.getPriority().value + " ");
        sb.append(PREFIX_DUEDATE + task.getDueDate().date + " ");
        return sb.toString();
    }
}
