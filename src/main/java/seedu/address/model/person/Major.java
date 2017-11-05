//@@author heiseish
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent a student's major in the address book.
 * User might also add year of study as an additional information (optional)
 */
public class Major {
    public final String value;


    public Major(String major) {
        requireNonNull(major);
        this.value = major.trim();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && this.value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
