//@@author inGall
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * Deletes a reminder identified using it's last displayed index from the address book.
 */
public class DeleteReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException pnfe) {
            assert false : "The target reminder cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
