package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetask";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number(s) used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) OR INDEX/INDEX/...\n"
            + "Example 1: " + COMMAND_WORD + " 1" + " deletes the first task in the list.\n"
            + "Example 2: " + COMMAND_WORD + " 1/2/3" + " deletes the first, second and third tasks in the list\n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private final ArrayList<Index> targetIndex;

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    //@@author lancehaoh
    public DeleteTaskCommand(ArrayList<Index> targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author lancehaoh
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        for (Index i : targetIndex) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }

        String result = "";
        Collections.sort(targetIndex);
        for (Index i : targetIndex) {
            ReadOnlyTask taskToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deleteTask(taskToDelete);
                if (targetIndex.size() == 1) {
                    result = result.concat(taskToDelete.toString());
                } else {
                    result = result.concat("\n" + taskToDelete.toString());
                }
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

        }

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, result));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_DELETE_TASK_SUCCESS, result));
        }
    }

    //@@author lancehaoh
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
