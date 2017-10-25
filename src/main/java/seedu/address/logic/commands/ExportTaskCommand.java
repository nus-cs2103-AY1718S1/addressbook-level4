package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Export the information about a person as a line of code that can be
 * recognized by the Add command of 3W, for immigration purposes.
 */
public class ExportTaskCommand extends Command {

    public static final String COMMAND_WORD = "exportTask";
    public static final String COMMAND_ALIAS = "ept";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the details of the task identified "
            + "by the index number used in the last task listing. \n"
            + "Output will be in an addTask command format, which can be "
            + "directly given to 3W to execute.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "addTask %1$s";
    private final Index targetIndex;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ExportTaskCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex));
        ReadOnlyTask taskToExport = lastShownList.get(targetIndex.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        taskToExport.getTags().forEach(builder::append);
        String feedBack = String.format(MESSAGE_SUCCESS,
                String.join(" ",
                        "n/" + taskToExport.getName(),
                        "d/" + taskToExport.getDescription(),
                        "s/" + taskToExport.getStartDateTime(),
                        "e/" + taskToExport.getEndDateTime(),
                        "t/" + builder.toString()));

        return new CommandResult(feedBack);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((ExportTaskCommand) other).targetIndex)); // state check
    }

}
