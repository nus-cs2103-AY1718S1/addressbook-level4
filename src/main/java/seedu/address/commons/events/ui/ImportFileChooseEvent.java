package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.storage.FileWrapper;

//@@author freesoup
/**
 * Indicates a request to open a FileChooser Window to import a file.
 */
public class ImportFileChooseEvent extends BaseEvent {

    private final FileWrapper file;

    public ImportFileChooseEvent (FileWrapper file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public FileWrapper getFile() {
        return this.file;
    }
}
