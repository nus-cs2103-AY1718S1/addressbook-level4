package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DateTimeParserUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Reschedules a task in the task list
 */
public class RescheduleTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "reschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the times of the task identified "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "1. by [DEADLINE] "
            + "2. from [START TIME] to [END TIME] "
            + "Example: " + COMMAND_WORD + " 1 from 8am to 12pm";

    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
    private static final String MESSAGE_RESCHEDULE_SUCCESS = "Task rescheduled: %1$s";

    private Index index;
    private Optional<LocalDateTime> newStartDateTime = Optional.empty();
    private Optional<LocalDateTime> newEndDateTime = Optional.empty();

    /**
     * Empty Constructor
     */
    public RescheduleTaskCommand() {
    }

    public RescheduleTaskCommand(Index targetIndex, Optional<LocalDateTime> startTime,
                                 Optional<LocalDateTime> endTime) throws IllegalValueException {

        requireNonNull(targetIndex);
        Optional<LocalDateTime> balancedEndTime = endTime;
        if (startTime.isPresent() && endTime.isPresent()) {
            balancedEndTime = Optional.of(
                    DateTimeParserUtil.balanceStartAndEndDateTime(startTime.get(), endTime.get()));
        }
        this.index = targetIndex;
        this.newEndDateTime = balancedEndTime;
        this.newStartDateTime = startTime;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        ObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToReschedule = lastShownList.get(index.getZeroBased());
        Task rescheduledTask = new Task(taskToReschedule);
        rescheduledTask.setStartDateTime(newStartDateTime);
        rescheduledTask.setEndDateTime(newEndDateTime);

        try {
            model.updateTask(taskToReschedule, rescheduledTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_RESCHEDULE_SUCCESS, rescheduledTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RescheduleTaskCommand)) {
            return false;
        }

        // state check
        RescheduleTaskCommand e = (RescheduleTaskCommand) other;
        return index.equals(e.index)
                && newEndDateTime.equals(e.newEndDateTime)
                && newStartDateTime.equals(e.newStartDateTime);
    }

}
