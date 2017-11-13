package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

// @@author johnweikangong
/**
 * Indicates a request to export addressbook to CSV file.
 */
public class ExportToFileRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
