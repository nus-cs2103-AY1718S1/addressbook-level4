package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author Jeremy
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; can take in any strings including blanks
 */
public class Remark {

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
