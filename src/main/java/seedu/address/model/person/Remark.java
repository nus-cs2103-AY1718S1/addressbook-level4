package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark implements Comparable {

    public static final String MESSAGE_REMARKS_CONSTRAINTS =
            "Person remarks can take any values, and it can be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        assert remark != null;
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

    @Override
    public int compareTo(Object o) {
        Remark comparedRemark = (Remark) o;
        return this.value.toLowerCase().compareTo(comparedRemark.toString().toLowerCase());
    }
}
