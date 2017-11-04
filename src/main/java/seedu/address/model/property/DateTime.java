package seedu.address.model.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.util.NaturalLanguageUtil;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

//@@author junyango
/**
 * Represents an event's date/time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DateTime extends Property {
    private static final String PROPERTY_SHORT_NAME = "dt";

    // The standard format for storing a date time in string format.
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");
    private static final String STANDARD_FORMAT = "^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}"
            + "(\\s((0[1-9]|1[0-9]|2[0-3]):([0-5][0-9]))?$)";

    public DateTime(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, prepareDateTimeValue(value));
    }

    public DateTime(Date value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, formatDateTime(value));
    }

    //@@author yunpengn
    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTime(String test) {
        try {
            prepareDateTimeValue(test);
            return true;
        } catch (IllegalValueException | PropertyNotFoundException e) {
            return false;
        }
    }

    /**
     * Prepares the value by checking whether the input can be interpreted by the natural language parser.
     */
    public static String prepareDateTimeValue(String value) throws IllegalValueException, PropertyNotFoundException {
        // Returns the original value directly if it is already in standard format.
        if (isInStandardFormat(value)) {
            return value;
        }

        Optional<Date> dateObject = NaturalLanguageUtil.parseSingleDateTime(value);
        if (dateObject.isPresent()) {
            return formatDateTime(dateObject.get());
        } else {
            throw new IllegalValueException(PropertyManager.getPropertyConstraintMessage(PROPERTY_SHORT_NAME));
        }
    }

    /**
     * Checks whether a string representation of datetime is in standard format.
     */
    public static boolean isInStandardFormat(String value) {
        return value.matches(STANDARD_FORMAT);
    }

    public static Date parseDateTime(String value) throws ParseException {
        return dateFormatter.parse(value);
    }

    public static String formatDateTime(Date date) {
        return dateFormatter.format(date);
    }
}
