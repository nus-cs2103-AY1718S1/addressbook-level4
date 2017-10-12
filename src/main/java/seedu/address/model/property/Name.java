package seedu.address.model.property;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends Property {
    private static final String PROPERTY_SHORT_NAME = "n";

    public Name(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
