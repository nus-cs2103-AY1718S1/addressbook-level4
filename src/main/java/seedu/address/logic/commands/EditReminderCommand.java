package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.Time;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;

//@@author justinpoh
/**
 * Edits the details of an existing reminder in iContacts.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editreminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER + "REMINDER]"
            + "[" + PREFIX_DATE + "DATE]"
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Additionally, if REMINDER is edited, the new value must contain at least one character.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER + "Changed reminder";


    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in iContacts.";

    public static final String MESSAGE_REMINDER_FORMAT = "Reminder can be of any value, and cannot be empty.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the sorted reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = editReminderDescriptor;
    }


    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyReminder> reminderList = model.getSortedReminderList();

        if (index.getZeroBased() >= reminderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToEdit = reminderList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        try {
            model.updateReminder(reminderToEdit, editedReminder);
        } catch (DuplicateReminderException dre) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder (ReadOnlyReminder reminderToEdit,
                                                  EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        String updatedReminder = editReminderDescriptor.getReminder().orElse(reminderToEdit.getReminder());
        Date updatedDate = editReminderDescriptor.getDate().orElse(reminderToEdit.getDate());
        Time updatedTime = editReminderDescriptor.getTime().orElse(reminderToEdit.getTime());

        return new Reminder(updatedReminder, updatedDate, updatedTime);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReminderCommand)) {
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
        private String reminder;
        private Time time;
        private Date date;

        public EditReminderDescriptor() {}

        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            this.reminder = toCopy.reminder;
            this.time = toCopy.time;
            this.date = toCopy.date;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.reminder, this.time, this.date);
        }

        public void setReminder(String reminder) {
            this.reminder = reminder;
        }

        public Optional<String> getReminder() {
            return Optional.ofNullable(reminder);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public void setDate(Date date) {
            this.date = date;
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

            return getReminder().equals(e.getReminder())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime());
        }
    }
}
