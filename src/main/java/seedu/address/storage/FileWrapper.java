package seedu.address.storage;

import java.io.File;

//@@author freesoup
/**
 * A File Wrapper class to allow modification of File Object after it has been created.
 */
public class FileWrapper {
    private File file;

    public FileWrapper() {
        this.file = null;
    }

    public FileWrapper(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
