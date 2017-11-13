package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lincredibleJC
/**
 * Represents a Person's FormClass name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFormClass(String)}
 */
public class FormClass {

    public static final String MESSAGE_FORMCLASS_CONSTRAINTS =
            "FormClass names should be alphanumeric and can contain '.' and '-' ";

    public static final String FORMCLASS_VALIDATION_REGEX = "^[a-zA-Z0-9\\.\\-\\/]+$";

    public final String value;

    /**
     * Validates given FormClass.
     *
     * @throws IllegalValueException if given FormClass string is invalid.
     */
    public FormClass(String formClass) throws IllegalValueException {
        requireNonNull(formClass);
        String trimmedFormClass = formClass.trim();
        if (!isValidFormClass(trimmedFormClass)) {
            throw new IllegalValueException(MESSAGE_FORMCLASS_CONSTRAINTS);
        }
        this.value = trimmedFormClass.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid formClass name.
     */
    public static boolean isValidFormClass(String test) {
        return test.matches(FORMCLASS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FormClass // instanceof handles nulls
                && this.value.equals(((FormClass) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
