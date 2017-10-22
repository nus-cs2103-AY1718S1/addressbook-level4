package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.BackupDataEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Creates a backup of the address book.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "b";
    public static final String MESSAGE_SUCCESS = "Data has been backed up";

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook backupAddressBookData = model.getAddressBook();
        EventsCenter.getInstance().post(new BackupDataEvent(backupAddressBookData));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
