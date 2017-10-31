package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Marks task(s) identified using it's last displayed index in the task listing.
 */
public class MarkTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the identified task(s) as done "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX START (must be a positive integer) ~ INDEX END(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " ~" + " 3";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task(s): %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final List<Index> targetIndices = new ArrayList<>();
    private ArrayList<ReadOnlyTask> tasksToMark;

    public MarkTaskCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public MarkTaskCommand(List<Index> indices) {
        this.targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyTask> tasksToMark = new ArrayList<ReadOnlyTask>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            ReadOnlyTask taskToMark = lastShownList.get(targetIndex.getZeroBased());
            tasksToMark.add(taskToMark);
            counter++;
        }

        try {
            model.markTasks(tasksToMark);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (ReadOnlyTask toAppend : tasksToMark) {
            builder.append(toAppend.toString());
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new

                CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkTaskCommand // instanceof handles nulls
                && this.targetIndices.equals(((MarkTaskCommand) other).targetIndices)); // state check
    }
}
