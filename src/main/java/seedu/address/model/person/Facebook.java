package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author HanYaodong
/**
 * Represents a Person's Facebook ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFacebookId(String)}
 */
public class Facebook {
    public static final String MESSAGE_FACEBOOK_CONSTRAINTS =
        "Person facebook should be in the format ";
    public static final String FACEBOOK_ID_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given facebook.
     *
     * @throws IllegalValueException if given facebook string is invalid.
     */
    public Facebook(String facebookId) throws IllegalValueException {
        requireNonNull(facebookId);
        if (facebookId.isEmpty()) {
            this.value = "";
            return;
        }
        String trimmedFacebookId = facebookId.trim();
        if (!isValidFacebookId(trimmedFacebookId)) {
            throw new IllegalValueException(MESSAGE_FACEBOOK_CONSTRAINTS);
        }
        this.value = trimmedFacebookId;
    }

    /**
     * Creates Birthday that is empty.
     */
    private Facebook() {
        this.value = "";
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidFacebookId(String test) {
        return test.matches(FACEBOOK_ID_VALIDATION_REGEX);
    }

    /**
     * Returns an empty Birthday object.
     */
    public static Facebook getEmptyFacebook() {
        return new Facebook();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Facebook // instanceof handles nulls
            && this.value.equals(((Facebook) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
