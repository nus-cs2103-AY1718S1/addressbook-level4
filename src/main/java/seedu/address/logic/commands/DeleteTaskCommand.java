package seedu.address.logic.commands;
//@@author Esilocke
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task from the address book.
 */
public class DeleteTaskCommand extends DeleteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TASK
            + ": Deletes the task identified by the index number used in the last listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> tasksList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= tasksList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        try {
            ReadOnlyTask taskToDelete = tasksList.get(targetIndex.getZeroBased());
            model.deleteTask(taskToDelete);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDelete));
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
    }




    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
