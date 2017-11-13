package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
/**
 * Represents a Person's username on Facebook.
 */
public class FacebookUsername {

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String username;

    public FacebookUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedName = username.trim();
        this.username = trimmedName;
    }
    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.username.equals(((FacebookUsername) other).username)); // state check
    }

}
