package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
/**
 * Represents a Person's File Path of the image he/she is assigned
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class FileImage {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "File Path must be correctly entered, that is the image must exist in the path specified\n"
            + "For example: src/resources/images/clock.png";

    public final String filePath;

    public FileImage(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        if (!isValidImage(filePath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        String trimmedName = filePath.trim();

        this.filePath = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Image path.
     */
    public static boolean isValidImage(String filePath) {

        String trimmedPath = filePath.trim();

        if (trimmedPath.isEmpty()) {
            return true;
        }
        File file = new File(trimmedPath);
        return file.exists();

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
