package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represent a person's portrait
 * Only store the file path of image
 */
public class PortraitPath {

    public static final String MESSAGE_PORTRAIT_CONSTRAINTS =
            "The portrait path should be alphanumeric string and '/', with valid suffix.\n"
            + "The application only supports .png and .jpg portrait files";

    public static final String PORTRAIT_VALIDATION_REGEX = "[A-Z]:[\\w\\s/]+\\.(?:png$|jpg$)";

    public static final String DEFAULT_PORTRAIT_PATH = "/images/sample.png";
    public static final String FILE_PREFIX = "file:///";
    public static final String EMPTY_PORTRAIT_PATH = "";

    public final String filePath;

    public PortraitPath(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        //Sometimes users may paste file path with '\' instead of '/'
        String trimmedPath = filePath.trim().replace('\\', '/');

        if (!isValidPortraitPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_PORTRAIT_CONSTRAINTS);
        }
        this.filePath = trimmedPath;
    }

    /**
     * Returns if a given portrait file is valid.
     */
    public static boolean isValidPortraitPath(String test) {
        return test.isEmpty() || test.matches(PORTRAIT_VALIDATION_REGEX);
    }

    /**
     * return true if the file path can really locate a file
     */
    public static boolean isValidUrl(String test) {
        return new File(test).exists();
    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PortraitPath // instanceof handles nulls
                && this.filePath.equals(((PortraitPath) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
