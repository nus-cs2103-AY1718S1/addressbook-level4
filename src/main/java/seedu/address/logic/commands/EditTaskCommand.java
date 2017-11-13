package seedu.address.logic.commands;
//@@author Esilocke
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Assignees;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskAddress;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Edits a task in the address book.
 */
public class EditTaskCommand extends EditCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD  + " " + PREFIX_TASK
            + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "write 1200-word essay "
            + PREFIX_PRIORITY + "1";
    public static final String MESSAGE_SUCCESS = "Edited Task: \n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final EditTaskDescriptor editTaskDescriptor;
    private final Index index;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        try {
            if (index.getZeroBased() >= lastShownTaskList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask taskToEdit = lastShownTaskList.get(index.getZeroBased());
            Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            model.updateTask(taskToEdit, editedTask);
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                         EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedTaskName;
        Description updatedDescription;
        Deadline updatedDeadline;
        Priority updatedPriority;
        Assignees assignees;
        Boolean updatedState;
        TaskAddress updatedTaskAddress;

        updatedTaskName = editTaskDescriptor.getTaskName().orElse(taskToEdit.getTaskName());
        updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        updatedDeadline = editTaskDescriptor.getDeadline().orElse(taskToEdit.getDeadline());
        updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());
        // You cannot edit assignees or state using edit command
        assignees = taskToEdit.getAssignees();
        updatedState = taskToEdit.getCompleteState();
        updatedTaskAddress = editTaskDescriptor.getTaskAddress().orElse(taskToEdit.getTaskAddress());

        return new Task(updatedTaskName, updatedDescription, updatedDeadline, updatedPriority, assignees, updatedState,
                updatedTaskAddress);
    }

    //@@author
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

        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index) && editTaskDescriptor.equals(e.editTaskDescriptor);
    }


    //@@author Esilocke
    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskName taskName;
        private Description description;
        private Deadline deadline;
        private Priority priority;
        private Assignees assignees;
        private TaskAddress taskAddress;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskName = toCopy.taskName;
            this.description = toCopy.description;
            this.deadline = toCopy.deadline;
            this.priority = toCopy.priority;
            this.assignees = toCopy.assignees;
            this.taskAddress = toCopy.taskAddress;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.taskName, this.description, this.deadline, this.priority,
                    this.taskAddress);
        }

        public void setTaskName(TaskName taskName) {
            this.taskName = taskName;
        }

        public Optional<TaskName> getTaskName() {
            return Optional.ofNullable(taskName);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline()  {
            return Optional.ofNullable(deadline);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setTaskAddress(TaskAddress taskAddress) {
            this.taskAddress = taskAddress;
        }

        public Optional<TaskAddress> getTaskAddress() {
            return Optional.ofNullable(taskAddress);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getTaskName().equals(e.getTaskName())
                    && getDescription().equals(e.getDescription())
                    && getDeadline().equals(e.getDeadline())
                    && getPriority().equals(e.getPriority())
                    && getTaskAddress().equals(e.getTaskAddress());
        }
    }
}
