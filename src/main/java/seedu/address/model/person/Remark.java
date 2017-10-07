package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's remark in the addressbook
 * Guarantees: Immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark (String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //Short circuit if same object
            || (other instanceof Remark)     //instanceof handles nulls
            && this.value.equals(((Remark) other).value);    // State check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
