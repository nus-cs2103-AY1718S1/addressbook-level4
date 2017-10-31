package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author junming403
/**
 * Represents a Module's remark(if any) in the application.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark can only be no more than 150 characters and cannot be empty";

    public static final int REMARK_LENGTH_LIMIT = 150;

    public final String value;
    public final Code moduleCode;


    /**
     * Validates given group number.
     *
     * @throws IllegalValueException if given group string is invalid.
     */
    public Remark(String remark, Code module) throws IllegalValueException {
        requireNonNull(remark);
        if (isValidRemark(remark)) {
            throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
        }
        value = remark;
        moduleCode = module;
    }

    /**
     * Creates a copy of the given Remark.
     */
    public Remark(Remark source) {
        value = source.value;
        moduleCode = source.moduleCode;
    }

    /**
     * Returns true if a given string is a valid Remark.
     */
    public static boolean isValidRemark(String test) {
        test = test.trim();
        return test.length() <= REMARK_LENGTH_LIMIT && test.length() > 0;
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
