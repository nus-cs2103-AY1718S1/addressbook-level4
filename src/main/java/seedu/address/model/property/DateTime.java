package seedu.address.model.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    // Change the regular expression in PropertyManager whenever you change this.
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");

    public DateTime(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, prepareDateTimeValue(value));
    }

    public DateTime(Date value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, formatDateTime(value));
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTime(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }

    //@@author yunpengn
    /**
     * Prepares the value by checking whether the input is in standard format: if it is, directly return its value;
     * otherwise, tries to parse it using natural language parser.
     */
    public static String prepareDateTimeValue(String value) throws IllegalValueException, PropertyNotFoundException {
        // Return it directly if the input is already in standard format.
        if (isValidTime(value)) {
            return value;
        }

        // Replace the value with standard format if the natural language parsing is successful; otherwise, return
        // the original value.
        return NaturalLanguageUtil.parseSingleDateTime(value).map(DateTime::formatDateTime).orElse(value);
    }

    public static Date parseDateTime(String value) throws ParseException {
        return dateFormatter.parse(value);
    }

    public static String formatDateTime(Date date) {
        return dateFormatter.format(date);
    }
}
