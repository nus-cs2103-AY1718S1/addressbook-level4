package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

/**
 * Represents a reminder's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public final String date;

    /**
     * Validates given date.
     */
    public Date(String date) {
        requireNonNull(date);
        this.date = date.trim();
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
