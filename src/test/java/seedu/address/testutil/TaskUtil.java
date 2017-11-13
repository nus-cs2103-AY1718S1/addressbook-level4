//@@author yuzu1209
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

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
        sb.append(PREFIX_NAME + task.getName().name + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
        sb.append(PREFIX_START_DATE_TIME + task.getStartDateTime().getState() + " ");
        sb.append(PREFIX_END_DATE_TIME + task.getEndDateTime().getState() + " ");
        task.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
