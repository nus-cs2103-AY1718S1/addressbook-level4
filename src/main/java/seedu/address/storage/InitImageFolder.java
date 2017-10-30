package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

/**
 * This class creates a image storage folder in the same directory as the addressbook jar file upon running
 * the main app.
 */
public class InitImageFolder {

    public InitImageFolder(String destinationPath) throws IOException {
        requireNonNull(destinationPath);
        File file = new File(destinationPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
