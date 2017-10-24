package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the path of an image for a Person in the address book
 */
public class Image {

    public final String path;

    public Image(String path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && this.path.equals(((Image) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
