package seedu.address.model.place;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author aungmyin23
/**
 * Represents a Place's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Place website should contain http://www https://www";
    public static final String WEBSITE_VALIDATION_REGEX =
            "https?://(www\\.)?[-a-z0-9]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)";
    public static final String WEBSITE_UNKNOWN = "http://www.-.com";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given website string is invalid.
     */
    public Website(String website) throws IllegalValueException {
        if (website == null) {
            this.value = WEBSITE_UNKNOWN;
        } else {
            String trimmedWebsite = website.trim();
            if (!isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException((MESSAGE_WEBSITE_CONSTRAINTS));
            }
            this.value = trimmedWebsite;
        }
    }
    //@@author
    /**
     * Returns if a given string is a valid place email.
     */
    public static boolean isValidWebsite(String test) {
        return test.matches(WEBSITE_VALIDATION_REGEX) || test.equals(WEBSITE_UNKNOWN);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Website // instanceof handles nulls
                && this.value.equals(((Website) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
