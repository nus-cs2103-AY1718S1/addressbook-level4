package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lincredibleJC
/**
 * Represents a Person's Grades in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrades(String)}
 */
public class Grades {

    public static final String MESSAGE_GRADES_CONSTRAINTS =
            "Grades can only contain positive numbers up to 2 decimal points";

    public static final String GRADES_VALIDATION_REGEX = "^((\\d|[1-9]\\d+)(\\.\\d{1,2})?|\\.\\d{1,2})$";

    public final String value;

    /**
     * Validates given Grades.
     *
     * @throws IllegalValueException if given Grades string is invalid.
     */
    public Grades(String grades) throws IllegalValueException {
        requireNonNull(grades);
        String trimmedGrades = grades.trim();
        if (!isValidGrades(trimmedGrades)) {
            throw new IllegalValueException(MESSAGE_GRADES_CONSTRAINTS);
        }
        this.value = trimmedGrades;
    }

    /**
     * Returns true if a given string is a valid Grades.
     */
    public static boolean isValidGrades(String test) {
        return test.matches(GRADES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.value.equals(((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }



}
