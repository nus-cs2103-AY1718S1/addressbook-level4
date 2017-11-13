//@@author wishingmaid
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

/**
 * Represents a Photo in the address book.
 */

public class Photo {
    public static final String URL_VALIDATION = "The filepath URL does not exist.";
    private static final String DEFAULT_FILEPATH = "";
    private String filepath;
    public Photo(String filepath) throws IllegalArgumentException {
        //this is to setup the default photo for contacts after it is added.
        requireNonNull(filepath);
        if (filepath.equals(DEFAULT_FILEPATH)) {
            this.filepath = DEFAULT_FILEPATH;
        } else {
            File file = new File(filepath);
            if (isValidFilePath(file)) {
                this.filepath = filepath;
            } else {
                throw new IllegalArgumentException(URL_VALIDATION);
            }
        }
    }

    public boolean isValidFilePath(File file) {
        return file.exists();
    }
    //the filepath of the image
    public String getFilePath() {
        return filepath;
    }
    /** It is guaranteed that the new filepath exists inside the resources folder */
    public void resetFilePath(String filepath) {
        this.filepath = filepath;
    }
    @Override
    public String toString() {
        return filepath;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.filepath.equals(((Photo) other).filepath)); // state check
    }
    @Override
    public int hashCode() {
        return filepath.hashCode();
    }
}
