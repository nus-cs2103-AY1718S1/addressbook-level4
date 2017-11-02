package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author nahtanojmil
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String REMARK_IF_EMPTY = "(add a remark)";

    public final String value;

    /**
     * Validates given remark.
     *
     */
    public Remark(String remark) {
        requireNonNull(remark);
        if (remark.equals("")) {
            this.value = REMARK_IF_EMPTY;
        } else {
            this.value = remark;
        }
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

