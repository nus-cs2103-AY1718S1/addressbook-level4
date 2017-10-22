package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.RestoreBackupDataEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Replace the current address book with data from backup address book.
 */
public class RestoreBackupCommand extends Command {
    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_ALIAS = "rb";
    public static final String MESSAGE_SUCCESS = "Data has been restored";

    @Override
    public CommandResult execute() throws CommandException {
        RestoreBackupDataEvent event = new RestoreBackupDataEvent();
        EventsCenter.getInstance().post(event);
        ReadOnlyAddressBook backupAddressBookData = event.getAddressBookData();
        model.resetData(backupAddressBookData);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
