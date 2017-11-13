package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_AT;

import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;
//@@author raisa2010
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
        sb.append(task.getDeadline().isEmpty() ? "" : PREFIX_DEADLINE_BY + " " + task.getDeadline().date.toString()
                + " ");
        sb.append((task.getStartTime().isPresent() | task.getEndTime().isPresent()) ? PREFIX_TIME_AT : " ");
        sb.append(" " + task.getStartTime().time.toString() + " ");
        sb.append(task.getEndTime().isPresent() ? " " + PREFIX_ENDTIME_TO + " " + task.getEndTime() + " " : "");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );

        return sb.toString();
    }
}
