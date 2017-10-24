package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing reminder in the address book.
 */
public class EditReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number used in the last reminder listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK + "TASK] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_MESSAGE + "MESSAGE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "7 "
            + PREFIX_DATE + "16/02/2017 1630";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the address book.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the filtered reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToEdit = lastShownList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        try {
            model.updateReminder(reminderToEdit, editedReminder);
        } catch (DuplicateReminderException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder(ReadOnlyReminder reminderToEdit,
                                             EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        Task updatedTask = editReminderDescriptor.getTask().orElse(reminderToEdit.getTask());
        Priority updatedPriority = editReminderDescriptor.getPriority().orElse(reminderToEdit.getPriority());
        Date updatedDate = editReminderDescriptor.getDate().orElse(reminderToEdit.getDate());
        Message updatedMessage = editReminderDescriptor.getMessage().orElse(reminderToEdit.getMessage());
        Set<Tag> updatedTags = editReminderDescriptor.getTags().orElse(reminderToEdit.getTags());

        return new Reminder(updatedTask, updatedPriority, updatedDate, updatedMessage, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderDescriptor {
        private Task task;
        private Priority priority;
        private Date date;
        private Message message;
        private Set<Tag> tags;

        public EditReminderDescriptor() {}

        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            this.task = toCopy.task;
            this.priority = toCopy.priority;
            this.date = toCopy.date;
            this.message = toCopy.message;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.task, this.priority, this.date,
                    this.message, this.tags);
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Optional<Task> getTask() {
            return Optional.ofNullable(task);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public Optional<Message> getMessage() {
            return Optional.ofNullable(message);
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
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getTask().equals(e.getTask())
                    && getPriority().equals(e.getPriority())
                    && getDate().equals(e.getDate())
                    && getMessage().equals(e.getMessage())
                    && getTags().equals(e.getTags());
        }
    }
}
