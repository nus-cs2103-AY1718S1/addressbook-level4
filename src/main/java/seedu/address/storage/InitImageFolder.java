package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

public class InitImageFolder {

    public InitImageFolder(String destinationPath) throws IOException {
        requireNonNull(destinationPath);
        File file = new File(destinationPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
