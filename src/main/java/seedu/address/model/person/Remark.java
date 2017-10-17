package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Remark for a Person in the address book
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARK_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String remark;

    /**
     * Validates given remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
