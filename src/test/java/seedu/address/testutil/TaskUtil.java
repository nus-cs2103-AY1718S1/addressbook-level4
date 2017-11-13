//@@author srishag
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class for Person.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_HEADER + task.getHeader().value + " ");
        sb.append(PREFIX_DESC + task.getDesc().value + " ");
        sb.append(PREFIX_DEADLINE + task.getDeadline().value + " ");
        return sb.toString();
    }
}
