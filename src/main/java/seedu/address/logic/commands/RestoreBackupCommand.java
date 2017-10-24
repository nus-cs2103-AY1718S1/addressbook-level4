package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.BackupFilePresentEvent;
import seedu.address.commons.events.storage.RestoreBackupDataEvent;
import seedu.address.commons.events.ui.RequestingUserPermissionEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Replace the current address book with data from backup address book.
 */
public class RestoreBackupCommand extends PermissionCommand {
    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_ALIAS = "rb";
    public static final String MESSAGE_SUCCESS = "Data has been restored";
    public static final String MESSAGE_FAILURE = "Data has not been restored";
    public static final String MESSAGE_WARNING =
            "Restoring backup will result in the lost of current data. Do you still want to proceed? y/n";
    public static final String MESSAGE_NO_BACKUP_FILE =
            "Unable to execute restore as there is no backup file available";

    @Override
    public CommandResult execute() throws CommandException {
        if (backupFilePresent()) {
            EventsCenter.getInstance().post(new RequestingUserPermissionEvent());
            return new CommandResult(String.format(MESSAGE_WARNING));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_BACKUP_FILE));
        }
    }

    @Override
    public CommandResult executeAfterUserPermission(boolean userPermission) throws CommandException {
        if (userPermission) {
            RestoreBackupDataEvent event = new RestoreBackupDataEvent();
            EventsCenter.getInstance().post(event);
            ReadOnlyAddressBook backupAddressBookData = event.getAddressBookData();
            model.resetData(backupAddressBookData);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE));
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
