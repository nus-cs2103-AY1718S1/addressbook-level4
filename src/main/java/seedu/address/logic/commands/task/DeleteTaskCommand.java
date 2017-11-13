package seedu.address.logic.commands.task;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author deep4k
/**
 * Deletes a task identified using it's last displayed index from the task listing.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX START (must be a positive integer) ~ INDEX END(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " ~" + " 3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s): %1$s";

    private final List<Index> targetIndices = new ArrayList<>();

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public DeleteTaskCommand(List<Index> indices) {
        targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex.getZeroBased() - counter);
            tasksToDelete.add(taskToDelete);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            counter++;
        }
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyTask toAppend : tasksToDelete) {
            builder.append("\n" + toAppend.toString());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndices.equals(((DeleteTaskCommand) other).targetIndices)); // state check
    }
}
