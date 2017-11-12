package seedu.address.model.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    //@@author yunpengn
    // To check whether the raw input is in standard format.
    private static final String INPUT_STANDARD_FORMAT = "^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}"
            + "(\\s((0[1-9]|1[0-9]|2[0-3]):([0-5][0-9]))?$)";
    // The formatter corresponding to raw input from user.
    private static final SimpleDateFormat inputFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");
    // The formatter corresponding to the format used in UI and storage.
    private static final SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM, yyyy HH:mm", Locale.ENGLISH);

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
    private static String prepareDateTimeValue(String value) throws IllegalValueException, PropertyNotFoundException {
        // Returns the original value directly if it is already in standard format.
        if (value.matches(INPUT_STANDARD_FORMAT)) {
            try {
                return formatDateTime(parseDateTime(value));
            } catch (ParseException e) {
                System.err.println("This should never happen. Format check has been performed.");
            }
        }

        Optional<Date> dateObject = NaturalLanguageUtil.parseSingleDateTime(value);
        if (dateObject.isPresent()) {
            return formatDateTime(dateObject.get());
        } else {
            throw new IllegalValueException(PropertyManager.getPropertyConstraintMessage(PROPERTY_SHORT_NAME));
        }
    }

    public static Date parseDateTime(String date) throws ParseException {
        return inputFormatter.parse(date);
    }

    /**
     * Converts the given {@link Date} object into the format used in UI and storage.
     */
    private static String formatDateTime(Date date) {
        return outputFormatter.format(date);
    }
}
