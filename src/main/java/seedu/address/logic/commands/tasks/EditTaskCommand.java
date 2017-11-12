package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@ raisa2010
/**
 * Edits the details of an existing task in the task manager.
 */
public class EditTaskCommand extends UndoableCommand {

    public static final int INDEX_START_TIME = 0;
    public static final int INDEX_END_TIME = 1;

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + "DESCRIPTION] "
            + "[" + PREFIX_DEADLINE_BY + "/" + PREFIX_DEADLINE_ON + "/" + PREFIX_DEADLINE_FROM + " DEADLINE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Task Descriptions containing deadline or time prefixes must be in double quotes [\"\"].\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE_BY + " 10-25-2017 [dates must be in (M)M(d)d(yy)yy format]";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskCommand.EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit.
     * @param editTaskDescriptor details to edit the task with.
     */
    public EditTaskCommand(Index index, EditTaskCommand.EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskCommand.EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElse(taskToEdit.getDeadline());
        EventTime updatedStartTime = editTaskDescriptor.getStartTime().orElse(taskToEdit.getStartTime());
        EventTime updatedEndTime = editTaskDescriptor.getEndTime().orElse(taskToEdit.getEndTime());
        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());

        return new Task(updatedDescription, updatedDeadline, updatedStartTime, updatedEndTime,
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Description description;
        private Deadline deadline;
        private EventTime startTime;
        private EventTime endTime;
        private Set<Tag> tags;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskCommand.EditTaskDescriptor toCopy) {
            this.description = toCopy.description;
            this.deadline = toCopy.deadline;
            this.startTime = toCopy.startTime;
            this.endTime = toCopy.endTime;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.description, this.deadline, this.startTime,
                    this.endTime, this.tags);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setEventTimes(EventTime[] eventTimes) {
            this.startTime = eventTimes[INDEX_START_TIME];
            this.endTime = eventTimes[INDEX_END_TIME];
        }

        public void setStartTime(EventTime startTime) {
            this.startTime = startTime;
        }

        public Optional<EventTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(EventTime endTime) {
            this.endTime = endTime;
        }

        public Optional<EventTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskCommand.EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskCommand.EditTaskDescriptor e = (EditTaskCommand.EditTaskDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getDeadline().equals(e.getDeadline())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getTags().equals(e.getTags());
        }
    }
}
