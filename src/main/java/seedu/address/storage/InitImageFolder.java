//@@author wishingmaid
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * This class creates a image storage folder in the same directory as the addressbook jar file upon running
 * the main app.
 */
public class InitImageFolder {

    private static final Logger logger = LogsCenter.getLogger(InitImageFolder.class);
    public InitImageFolder(String destinationPath) throws IOException {
        requireNonNull(destinationPath);
        File file = new File(destinationPath);
        if (!file.exists()) {
            logger.info("Created " + destinationPath + "folder");
            file.mkdir();
        }
    }
}
