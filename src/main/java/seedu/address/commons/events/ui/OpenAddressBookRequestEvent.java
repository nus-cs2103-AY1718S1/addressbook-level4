package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to open a new AddressBook
 */
public class OpenAddressBookRequestEvent extends BaseEvent {

    private String filePath;

    /**
     *
     * @param filePath to the new AddressBook
     */
    public OpenAddressBookRequestEvent(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() { return this.getClass().getSimpleName(); }
}
