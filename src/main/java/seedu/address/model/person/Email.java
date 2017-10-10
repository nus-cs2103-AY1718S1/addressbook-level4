package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.exceptions.PropertyNotFoundException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email extends Property {
    private static final String PROPERTY_SHORT_NAME = "e";

    public Email(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid email address.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
