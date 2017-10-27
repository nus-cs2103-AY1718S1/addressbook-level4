package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;


/**
 * Sets the priority of a task identified as completed using it's last displayed index from the address book.
 */
public class SetPriorityCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setPriority";
    public static final String COMMAND_ALIAS = "stp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the priority by the new value as the user specified, which is between 1~5\n"
            + "Parameters: INDEX (must be a positive integer) PRIORITY (must be an integer between 1 and 5\n"
            + "Example: " + COMMAND_WORD + " 1 c/2";

    public static final String MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS = "Updated the Priority of Task %1$s";
    public static final String MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE = "Priority value should be 1~5";

    private final Index targetIndex;
    private final Integer priority;

    public SetPriorityCommand(Index targetIndex, Integer priority) {
        this.targetIndex = targetIndex;
        this.priority = priority;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Task updatedTask = createUpdatedTask(taskToUpdate, priority);

        // Checking if the priority value inputted is out of range
        if (updatedTask == null) {
            throw new CommandException(MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE);
        }

        try {
            model.updateTask(taskToUpdate, updatedTask);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException dte) {
            assert false : "The target updated can cause duplication";
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS, taskToUpdate.getName()));
    }

    /**
     * Create an updated task by only modifying its priority property
     * @param target , the targeted task to be edited
     * @param value , the new priority value
     * @return the updated task or null
     */
    public Task createUpdatedTask (ReadOnlyTask target, Integer value) {
        // Preliminary checking
        if (value < 0 || value > 5) {
            return null;
        }

        Task updatedTask = new Task(
                target.getName(),
                target.getDescription(),
                target.getStartDateTime(),
                target.getEndDateTime(),
                target.getTags(),
                target.getComplete(),
                value);

        return updatedTask;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPriorityCommand // instanceof handles nulls
                && this.targetIndex.equals(((SetPriorityCommand) other).targetIndex)); // state check
    }
}
