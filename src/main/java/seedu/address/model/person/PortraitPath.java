package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represent a person's portrait
 * Only store the file path of image
 */
public class PortraitPath {

    public static final String MESSAGE_PORTRAIT_CONSTRAINTS =
            "The portrait path should be alphanumeric string plus suffix"
            + "The application only supports .png and .jpg portrait files";

    public static final String PORTRAIT_VALIDATION_REGEX = "\\S[\\w\\s]+\\.(?:png$|jpg$)";

    private final String filePath;

    public PortraitPath(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedPath = filePath.trim();

        if (!isValidPortraitPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_PORTRAIT_CONSTRAINTS);
        }
        this.filePath = trimmedPath;
    }

    /**
     * Returns if a given portrait file is valid.
     */
    public static boolean isValidPortraitPath(String test) {
        return test.matches(PORTRAIT_VALIDATION_REGEX);
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
