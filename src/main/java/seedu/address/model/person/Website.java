package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's website in teh address book.
 * Guarantees: immutable; is valid as declared
 */
public class Website {
    public static final String MESSAGE_WEBSITE_CONSTRAINS =
            "Website can only contain https/http:// www.";
    public static final String WEBSITE_VALIDATION_REGEX =
            "https?://(www\\.)?[-a-z0-9]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)";
    public static final String WEBSITE_EXAMPLE = "http://www.website.com";
    public static final String WEBSITE_NULL = ""; // no website
    public final String value;

    /**
     *
     */
    public Website(String website)throws IllegalValueException {
        String trimmedWebsite = website == null ? WEBSITE_NULL : website.trim();
        if (!isValidWebsite(trimmedWebsite)) {
            throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINS);
        }

        this.value = website;
    }

    /**
     * Returns true if given string is valid person website
     */
    public static boolean isValidWebsite(String test) {
        return test.equals(WEBSITE_NULL) || test.matches(WEBSITE_VALIDATION_REGEX);
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

    public boolean hasWebsite() {
        return !value.equalsIgnoreCase(WEBSITE_NULL);
    }
}
