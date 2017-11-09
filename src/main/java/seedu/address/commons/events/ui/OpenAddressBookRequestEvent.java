package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;

//@@author chrisboo
/**
 * Indicates a request to open a new AddressBook
 */
public class OpenAddressBookRequestEvent extends BaseEvent {

    private String fileName;
    private String filePath;

    public OpenAddressBookRequestEvent(File file) {
        fileName = file.getName();
        filePath = file.getPath();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
