package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

//@@author RonakLakhotia
/**
 * Changes the details of an existing reminder in Weaver.
 */
public class ChangeReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the details of the reminder identified "
            + "by the index number used in the last reminder listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER_DETAILS + "DETAILS] "
            + "[" + PREFIX_REMINDER_PRIORITY + "PRIORITY LEVEL] "
            + "[" + PREFIX_REMINDER_DUE_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER_DUE_DATE + "12.11.2017 ";

    public static final String MESSAGE_CHANGE_REMINDER_SUCCESS = "Changed Reminder: %1$s";
    public static final String MESSAGE_NOT_CHANGED = "At least one field to change must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in weaver.";

    private final Index index;
    private final ChangeReminderDescriptor changeReminderDescriptor;

    /**
     * Changes the reminder at the given index with the given descriptor.
     * @param index of the reminder in the filtered reminder list to change
     * @param changeReminderDescriptor details to change the reminder with
     */
    public ChangeReminderCommand(Index index, ChangeReminderDescriptor changeReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(changeReminderDescriptor);

        this.index = index;
        this.changeReminderDescriptor = new ChangeReminderDescriptor(changeReminderDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToChange = lastShownList.get(index.getZeroBased());
        Reminder changedReminder = createChangedReminder(reminderToChange, changeReminderDescriptor);

        try {
            model.updateReminder(reminderToChange, changedReminder);
        } catch (DuplicateReminderException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(String.format(MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToChange}
     * edited with {@code changeReminderDescriptor}.
     */
    private static Reminder createChangedReminder(ReadOnlyReminder reminderToChange,
                                                  ChangeReminderDescriptor changeReminderDescriptor) {
        assert reminderToChange != null;

        ReminderDetails updateDetails = changeReminderDescriptor.getDetails().orElse(reminderToChange.getDetails());
        Priority updatePriority = changeReminderDescriptor.getPriority().orElse(reminderToChange.getPriority());
        DueDate updatedDate = changeReminderDescriptor.getDueDate().orElse(reminderToChange.getDueDate());

        return new Reminder(updateDetails, updatePriority, updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeReminderCommand)) {
            return false;
        }

        // state check
        ChangeReminderCommand command = (ChangeReminderCommand) other;
        return index.equals(command.index)
                && changeReminderDescriptor.equals(command.changeReminderDescriptor);
    }

    /**
     * Stores the details to change the reminder with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class ChangeReminderDescriptor {
        private ReminderDetails details;
        private Priority priority;
        private DueDate dueDate;

        public ChangeReminderDescriptor() {}

        public ChangeReminderDescriptor(ChangeReminderDescriptor toCopy) {
            this.details = toCopy.details;
            this.priority = toCopy.priority;
            this.dueDate = toCopy.dueDate;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldChanged() {
            return CollectionUtil.isAnyNonNull(this.details, this.priority, this.dueDate);
        }

        public void setDetails(ReminderDetails details) {
            this.details = details;
        }

        public Optional<ReminderDetails> getDetails() {
            return Optional.ofNullable(details);
        }

        public void setDueDate(DueDate dueDate) {
            this.dueDate = dueDate;
        }

        public Optional<DueDate> getDueDate() {
            return Optional.ofNullable(dueDate);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ChangeReminderDescriptor)) {
                return false;
            }

            // state check
            ChangeReminderDescriptor c = (ChangeReminderDescriptor) other;

            return getDetails().equals(c.getDetails())
                    && getPriority().equals(c.getPriority())
                    && getDueDate().equals(c.getDueDate());
        }
    }
}
