package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author deep4k
/**
 * Renames the header target task in the task listing.
 */
public class RenameTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rename";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the header the task identified "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[New header]"
            + "Example: " + COMMAND_WORD + " 2 Football training";

    public static final String MESSAGE_RENAME_TASK_SUCCESS = "Renamed Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task header already exists in the address book.";

    private final Index index;
    private Header newTaskHeader;

    public RenameTaskCommand(Index targetIndex, String header) throws IllegalValueException {
        requireNonNull(targetIndex);
        requireNonNull(header);
        this.index = targetIndex;
        this.newTaskHeader = new Header(header);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        ObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToRename = lastShownList.get(index.getZeroBased());
        Task renamedTask = new Task(taskToRename);
        renamedTask.setHeader(newTaskHeader);

        try {
            model.updateTask(taskToRename, renamedTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_RENAME_TASK_SUCCESS, renamedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RenameTaskCommand)) {
            return false;
        }

        // state check
        RenameTaskCommand e = (RenameTaskCommand) other;
        return index.equals(e.index)
                && newTaskHeader.equals(e.newTaskHeader);
    }
}
