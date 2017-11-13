package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

//@@author karrui
/**
 * A class to access the avatar file directory path on the hard disk.
 */
public class AvatarStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String directoryPath;

    public AvatarStorage(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        createDirectory(directoryPath);
    }

    /**
     * Creates a directory in specified path if directory does not exist
     */
    public void createDirectory(String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            logger.info("Failed to create directory at " + directoryPath);
        }
    }

    @Override
    public String toString() {
        return "Local avatar directory location : " + directoryPath;
    }
}
