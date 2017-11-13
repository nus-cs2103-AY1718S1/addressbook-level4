package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String PHONE_PLACEHOLDER_VALUE = "";
    public final String value;

    private boolean isPrivate = false;
    private int privacyLevel = 2;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone(String phone) throws IllegalValueException {
        if (phone == null) {
            this.value = PHONE_PLACEHOLDER_VALUE;
            return;
        }
        String trimmedPhone = phone.trim();
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    //@@author jeffreygohkw
    public Phone(String phone, boolean isPrivate) throws IllegalValueException {
        this(phone);
        this.setPrivate(isPrivate);
    }

    //@@author
    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX) || test.equals(PHONE_PLACEHOLDER_VALUE);
    }

    //@@author jeffreygohkw
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Phone>";
            }
            return value;
        }
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
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
