package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.BackupFilePresentEvent;
import seedu.address.commons.events.storage.RestoreBackupDataEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Replace the current address book with data from backup address book.
 */
public class RestoreBackupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_ALIAS = "rb";
    public static final String MESSAGE_SUCCESS = "Data has been restored";
    public static final String MESSAGE_NO_BACKUP_FILE =
            "Unable to execute restore as there is no backup file available";

    @Override
    protected CommandResult executeUndoableCommand() {
        if (backupFilePresent()) {
            RestoreBackupDataEvent event = new RestoreBackupDataEvent();
            EventsCenter.getInstance().post(event);
            ReadOnlyAddressBook backupAddressBookData = event.getAddressBookData();
            model.resetData(backupAddressBookData);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_BACKUP_FILE));
        }
    }

    /**
     * Checks if there is a backup file.
     */
    private boolean backupFilePresent() {
        BackupFilePresentEvent event = new BackupFilePresentEvent();
        EventsCenter.getInstance().post(event);
        return (event.getBackupFilePresenceStatus());
    }
}
