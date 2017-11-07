package seedu.address.model.person;
//@@author RonakLakhotia
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 */
public class FileImage {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "File Path must be correctly entered";

    public final String filePath;

    public FileImage(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedName = filePath.trim();

        this.filePath = trimmedName;
    }


    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileImage // instanceof handles nulls
                && this.filePath.equals(((FileImage) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

    public String getFilePath() {
        return filePath;
    }


}
