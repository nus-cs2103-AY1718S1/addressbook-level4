package seedu.address.commons.events.ui;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to open a new AddressBook
 */
public class OpenAddressBookRequestEvent extends BaseEvent {

    private String fileName;
    private String filePath;

    /**
     *
     * @param file that contains the addressbook xml
     */
    public OpenAddressBookRequestEvent(File file) {
        this.fileName = FilenameUtils.removeExtension(file.getName());
        this.filePath = file.getPath();
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
