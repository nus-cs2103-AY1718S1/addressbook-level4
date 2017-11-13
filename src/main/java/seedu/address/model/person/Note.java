//@@author sebtsh
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's note in the address book. A note is any string that the user wishes to input about a person
 * but cannot be adequately represented in tags (e.g. sentences-long details). Note is the only Person field that cannot
 * be initialized in an add command, it needs to be added after the Person is added to the address field.
 */

public class Note {
    public static final String MESSAGE_NOTE_CONSTRAINTS =
            "Person note can take any values, and it should not be blank";

    /*
     * The first character of the note must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOTE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given note.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Note(String note) throws IllegalValueException {
        requireNonNull(note);
        if (!isValidNote(note)) {
            throw new IllegalValueException(MESSAGE_NOTE_CONSTRAINTS);
        }
        this.value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(NOTE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && this.value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
