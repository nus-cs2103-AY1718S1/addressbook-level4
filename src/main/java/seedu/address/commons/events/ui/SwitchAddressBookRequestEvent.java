package seedu.address.commons.events.ui;

import static seedu.address.commons.util.FileUtil.getExtension;
import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.File;

import seedu.address.commons.events.BaseEvent;

//@@author chrisboo
/**
 * Indicates a request to switch (open existing / create new) AddressBook
 */
public class SwitchAddressBookRequestEvent extends BaseEvent {

    private String fileName;
    private String filePath;
    private boolean isNewFile;

    public SwitchAddressBookRequestEvent(File file, boolean isNewFile) {
        if (getExtension(file).equals("xml")) {
            throw new Exception("Invalid file!");
        }

        if (isNewFile && isFileExists(file)) {
            throw new Exception("Cannot overwrite existing file!");
        }

        fileName = file.getName();
        filePath = file.getPath();
        this.isNewFile = isNewFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isOpenNewAddressBook() { return isNewFile; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
