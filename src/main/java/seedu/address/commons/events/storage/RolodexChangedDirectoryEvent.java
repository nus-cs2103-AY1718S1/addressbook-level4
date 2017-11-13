package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a change in the loaded data filepath.
 */
public class RolodexChangedDirectoryEvent extends BaseEvent {

    private final String filePath;

    public RolodexChangedDirectoryEvent(String newFilePath) {
        this.filePath = newFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
