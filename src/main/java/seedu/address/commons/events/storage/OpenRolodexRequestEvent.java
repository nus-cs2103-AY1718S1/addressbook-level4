package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for a reload in application with a new Rolodex.
 */
public class OpenRolodexRequestEvent extends BaseEvent {

    private final String filePath;

    public OpenRolodexRequestEvent(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
