package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class ImageStorage {

    private String filePath;

    public ImageStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the image folder.
     */
    public String getAddressBookImagePath() {
        return filePath;
    }

    /**
     * Creates a new folder for image storage
     */
    public void createImageStorageFolder() throws IOException {
        requireNonNull(filePath);

        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
