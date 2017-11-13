package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
/**
 * Represents an Email's Subject line.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class EmailSubject {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Subject lines should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the subject must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String subject;

    /**
     * Validates given subject
     *
     * @throws IllegalValueException if given subject string is invalid.
     */
    public EmailSubject(String subject) throws IllegalValueException {

        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!isValidSubject(trimmedSubject)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.subject = subject;
    }

    /**
     * Returns true if a given string is a valid email subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailSubject // instanceof handles nulls
                && this.subject.equals(((EmailSubject) other).subject)); // state check
    }

    @Override
    public String toString() {
        return subject;
    }
}
