package seedu.address.logic.commands;
//@@author Esilocke
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestTaskEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Selects a task in the address book and loads the view panel for that task.
 */
public class SelectTaskCommand extends SelectCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TASK
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Selected Task: %1$s";

    private final Index targetIndex;

    public SelectTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestTaskEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectTaskCommand) other).targetIndex)); // state check
    }
}
