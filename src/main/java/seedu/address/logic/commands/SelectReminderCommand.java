//@@author cqhchan
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToReminderRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;


/**
 * Selects a reminder identified using it's last displayed index from the address book.
 */
public class SelectReminderCommand extends Command {

    public static final String COMMAND_WORD = "selectReminder";
    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the Reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_REMINDER_SUCCESS = "Selected Reminder: %1$s";

    private final Index targetIndex;

    public SelectReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToReminderRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_REMINDER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectReminderCommand) other).targetIndex)); // state check
    }
}

