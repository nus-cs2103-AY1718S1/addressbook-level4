package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.exceptions.PropertyNotFoundException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends Property {
    private static final String PROPERTY_SHORT_NAME = "p";

    public Phone(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
