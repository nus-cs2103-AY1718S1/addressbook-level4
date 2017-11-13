package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.BackUpEvent;
import seedu.address.logic.commands.exceptions.CommandException;
//@@author pjunwei95
/**
 * Backup the Address Book
 */
public class BackUpCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_SUCCESS = "A backup of Weaver has been created!";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new BackUpEvent(model.getAddressBook()));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BackUpCommand)) {
            return false;
        }

        // state check
        BackUpCommand e = (BackUpCommand) other;
        return model.getAddressBook().equals(e.model.getAddressBook());
    }
}
