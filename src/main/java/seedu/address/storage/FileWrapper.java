package seedu.address.storage;

import java.io.File;

public class FileWrapper {
    private File file;

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
