package seedu.address.logic.commands;
//@@author Esilocke

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Displays a list of all persons assigned to a specified task
 */
public class ViewAssignCommand extends Command {
    public static final String COMMAND_WORD = "viewassign";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all persons assigned to a task\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";
    private final Index taskIndex;

    public ViewAssignCommand(Index taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> tasksList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= tasksList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToFind = tasksList.get(taskIndex.getZeroBased());
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.viewAssignees(taskToFind);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof ViewAssignCommand
                && this.taskIndex.equals(((ViewAssignCommand) other).taskIndex);
    }
}
