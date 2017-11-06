package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to open a FileChooser Window to import a file.
 */
public class ImportFileChooseEvent extends BaseEvent {

    public static final File file;

    public ImportFileChooseEvent (File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
