package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.property.Property;
import seedu.address.ui.person.PersonDetailsPanel;

//@@author yunpengn
/**
 * Represents the {@link Avatar} image of each {@link Person}. This is a one-to-one relationship, meaning that each
 * {@link Person} should have at most one {@link Avatar}.<br>
 *
 * Notice {@link Avatar} is not a {@link Property}. This is because it is indeed different from other fields of
 * {@link Person}. It is not shown as a row in the {@link PersonDetailsPanel}. Meanwhile, the input validation is
 * done by separate methods rather than a single regular expression (the complexity is not at the same level).
 */
public class Avatar {
    public static final String INVALID_PATH_MESSAGE = "The provided image path is invalid.";
    public static final String IMAGE_NOT_EXISTS = "The provided image path does not exist.";
    public static final String FILE_NOT_IMAGE = "The provided file exists, but it is not an image.";

    private String path;
    private String uri;

    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        if (!isValidAvatarPath(path)) {
            throw new IllegalValueException(INVALID_PATH_MESSAGE);
        }

        File file = new File(path);
        if (!FileUtil.isFileExists(file)) {
            throw new IllegalValueException(IMAGE_NOT_EXISTS);
        }
        if (!FileUtil.isImage(file)) {
            throw new IllegalValueException(FILE_NOT_IMAGE);
        }

        this.path = path;
        this.uri = file.toURI().toString();
    }

    /**
     * An all-in-one checking for the path of the provided image.
     */
    private boolean isValidAvatarPath(String path) {
        return !FileUtil.hasConsecutiveExtensionSeparators(path)
                && !FileUtil.hasConsecutiveNameSeparators(path)
                && !FileUtil.hasInvalidNames(path)
                && !FileUtil.hasInvalidNameSeparators(path);
    }

    public String getPath() {
        return path;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.uri.equals(((Avatar) other).uri));
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public String toString() {
        return "Avatar from " + uri;
    }
}
