package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Requests to check if there is a backup file in the default path.
 */
public class BackupFilePresentEvent extends BaseEvent {
    private boolean backupFileIsPresent;

    public BackupFilePresentEvent() {
        this.backupFileIsPresent = false;
    }

    public void updateBackupFilePresenceStatus(boolean status) {
        backupFileIsPresent = status;
    }

    public boolean getBackupFilePresenceStatus() {
        return backupFileIsPresent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
