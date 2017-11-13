//@@author zenghou
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark about a Person in the address book.
 * Guarantees: immutable
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remarks can take any value, and it may be blank";

    public final String value;

    /**
     * Validates given remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    /**
     * Returns an empty Birthday object.
     */
    public static Remark getEmptyRemark() {
        return new Remark("");
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
