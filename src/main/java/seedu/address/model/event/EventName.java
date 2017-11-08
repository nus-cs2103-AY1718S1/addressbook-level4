package seedu.address.model.event;

//@@author chernghann
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Events's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EventName {

    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVENT_NAME_VALIDATION_REGEX = "[A-Za-z ']*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = toCapitalized(name.trim());
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.Name // instanceof handles nulls
                && this.fullName.equals(((seedu.address.model.person.Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    //@@author DarrenCzen
    /**
     * This method converts a name to become capitalized fully.
     * e.g. from "dArrEn cHiN" to "Darren Chin"
     */
    public static String toCapitalized(String s) {

        final String delimiters = " ";
        StringBuilder newString = new StringBuilder();
        boolean isCapital = true;

        for (char c : s.toCharArray()) {
            c = (isCapital) ? Character.toUpperCase(c) : Character.toLowerCase(c);
            newString.append(c);

            isCapital = (delimiters.indexOf((int) c) >= 0);
        }
        return newString.toString();
    }
    //@@author
}
