package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Marks a task identified as completed using it's last displayed index from the address book.
 * When list tasks again the marked tasks will not be shown, but can be reviewed when the history is listed.
 */
public class SetPriorityCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setPriority";
    public static final String COMMAND_ALIAS = "stp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the priority by the new value as the user specified, which is between 1~5\n"
            + "Parameters: INDEX (must be a positive integer) PRIORITY (must be an integer between 1 and 5\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Update Priority of Task: %1$s";

    private final Index targetIndex;
    private final Integer priority;

    public SetPriorityCommand(Index targetIndex, Integer priority) {
        this.targetIndex = targetIndex;
        this.priority = priority;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.updateTaskPriority(taskToUpdate, priority);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException dte) {
            assert false : "The target updated can cause duplication";
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToUpdate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPriorityCommand // instanceof handles nulls
                && this.targetIndex.equals(((SetPriorityCommand) other).targetIndex)); // state check
    }
}
