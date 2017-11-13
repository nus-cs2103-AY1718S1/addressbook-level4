package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;

//@@author justinpoh
/**
 * Deletes a reminder identified using it's index.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "deletereminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer).\n"
            + "Example: " + COMMAND_WORD + " 1.";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted reminder: %1$s.";

    private final Index targetIndex;

    public DeleteReminderCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyReminder> reminderListing = model.getSortedReminderList();

        if (targetIndex.getZeroBased() >= reminderListing.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToDelete = reminderListing.get(targetIndex.getZeroBased());

        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException rnfe) {
            assert false : "The target reminder cannot be missing.";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
