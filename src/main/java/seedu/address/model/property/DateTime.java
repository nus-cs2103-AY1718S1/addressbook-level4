package seedu.address.model.property;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

/**
 * Represents an event's date/time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DateTime extends Property {
    private static final String PROPERTY_SHORT_NAME = "dt";
    // Change the regular expression in PropertyManager whenever you change this.
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");

    public DateTime(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    public DateTime(Date value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, dateFormatter.format(value));
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTime(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
