package seedu.address.model.person;

import static java.util.Objects.requireNonNull;



/**
 * Represents a Person's appoint in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appoint {

    public static final String MESSAGE_APPOINT_CONSTRAINTS =
            "Person appointments should be recorded as DD/MM/YYYY TT:TT, but can be left blank";

    public final String value;

    public Appoint(String appoint) {
        requireNonNull(appoint);
        this.value = appoint;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appoint // instanceof handles nulls
                && this.value.equals(((Appoint) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
