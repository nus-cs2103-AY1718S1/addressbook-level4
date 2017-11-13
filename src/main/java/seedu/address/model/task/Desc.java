//@@author srishag
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDesc(String)}
 */
public class Desc {

    public static final String MESSAGE_DESC_CONSTRAINTS =
            "Task descriptions can take any values, and it should not be blank";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESC_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given desc string is invalid.
     */
    public Desc(String desc) throws IllegalValueException {
        requireNonNull(desc);
        if (!isValidDesc(desc)) {
            throw new IllegalValueException(MESSAGE_DESC_CONSTRAINTS);
        }
        this.value = desc;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDesc(String test) {
        return test.matches(DESC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Desc // instanceof handles nulls
                && this.value.equals(((Desc) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
