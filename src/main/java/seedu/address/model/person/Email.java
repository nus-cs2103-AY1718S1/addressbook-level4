package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";
    public static final String EMAIL_PLACEHOLDER_VALUE = "";

    public final String value;
    private boolean isPrivate = false;
    private int privacyLevel = 2;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        if (email == null) {
            this.value = EMAIL_PLACEHOLDER_VALUE;
            return;
        }
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }
    //@@author jeffreygohkw
    public Email(String email, boolean isPrivate) throws IllegalValueException {
        this(email);
        this.setPrivate(isPrivate);
    }

    //@@author
    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX) || test.equals(EMAIL_PLACEHOLDER_VALUE);
    }

    //@@author jeffreygohkw
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Email>";
            }
            return value;
        }
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    //@@author jeffreygohkw
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
