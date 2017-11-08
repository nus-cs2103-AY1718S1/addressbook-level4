package seedu.address.model.person;

import org.apache.commons.validator.routines.UrlValidator;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author DarrenCzen
/**
 * Represents a Person's website information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */

public class Website {
    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Website inputted should follow format https://www.anyName.com/anyContent"
                    + " where both anyName and anyContent can be alphanumeric."
                    + " You must have https://www. and a domain name like .com";
    public static final String WEBSITE_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Website(String websiteName) throws IllegalValueException {
        if (websiteName == null) {
            this.value =  WEBSITE_TEMPORARY;
        } else {
            String trimmedWebsite = websiteName.trim();

            if (!isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);
            }

            this.value = trimmedWebsite;
        }
    }

    /**
     * Returns if a given string is a valid person website.
     */
    public static boolean isValidWebsite(String test) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(test) || test.matches(WEBSITE_TEMPORARY);
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
