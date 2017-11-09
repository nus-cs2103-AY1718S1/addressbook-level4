//@@author fustilio
package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Parcel's delivery date in the address book.
 * Guarantees: mutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DeliveryDate {


    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Delivery dates should be in the format dd-mm-yyyy or references to date such as "
            + "\"today\" or \"next week.\"";
    public static final List<String> VALID_STRING_FORMATS = Arrays.asList(
            "dd-MM-yyyy", "d-MM-yyyy", "d-M-yyyy", "dd-M-yyyy",
            "dd/MM/yyyy", "d/MM/yyyy", "d/M/yyyy", "dd/M/yyyy",
            "dd.MM.yyyy", "d.MM.yyyy", "d.M.yyyy", "dd/M.yyyy");
    public static final String DATE_FORMAT_VALIDATION_REGEX = "^(\\d{1,2}[./-]\\d{1,2}[./-]\\d{4})$";
    public final String value;
    private Date date;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Validates given delivery date.
     *
     * @throws IllegalValueException if given delivery date string is invalid.
     */
    public DeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDate = deliveryDate.trim();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);

        // Check if input is in a format we can understand
        if (!isValidDateFormat(trimmedDate)) {
            // Check if input is in a format that PrettyTime(NLP) can understand
            if (isValidPrettyTimeDate(trimmedDate)
                    && hasMinimumLength(trimmedDate)
                    && !containsAllNumbers(trimmedDate)) {
                // NLP appears to understand the intention, so we accept the input
                List<Date> dates = new PrettyTimeParser().parse(trimmedDate);
                this.date = dates.get(0);
            } else {
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
            }
        } else { // We understand the intention, so we accept the input
            try {
                this.date = formatDate(trimmedDate);
            } catch (ParseException e) { // date is in correct format, but not a valid date.
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
            }
        }

        // Format date correctly
        this.value = df.format(this.date);
    }

    /**
     * Formats the input date according to the list VALID_STRING_FORMATS and returns it.
     */
    private Date formatDate(String inputDate) throws ParseException {

        for (String formatString : VALID_STRING_FORMATS) {
            DateFormat df = new SimpleDateFormat(formatString);
            df.setLenient(false);
            try {
                return df.parse(inputDate);
            } catch (ParseException e) {
                logger.info("Failed to fit input delivery date in current format, trying next format...");
            }
        }

        logger.warning("Exhausted all formats, not a valid input.");

        throw new ParseException(inputDate, 0);

    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidDate(String test) {
        DeliveryDate result;
        try {
            result = new DeliveryDate(test);
        } catch (IllegalValueException e) {
            return false;
        }
        return !result.equals(null);
    }

    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_FORMAT_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidPrettyTimeDate(String test) {
        List<Date> dates = new PrettyTimeParser().parse(test);

        return dates.size() > 0;
    }

    /**
     * Returns true if a given string is of a minimum length, more than 2 chars
     */
    public static boolean hasMinimumLength(String test) {
        return test.length() > 2;
    }

    /**
     * Returns true if a given string contains all numbers.
     */
    public static boolean containsAllNumbers(String test) {
        String regex = "\\d+";
        return test.matches(regex);
    }

    private Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDate // instanceof handles nulls
                && this.value.equals(((DeliveryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(DeliveryDate deliveryDate) {
        return this.date.compareTo(deliveryDate.getDate());
    }
}
//@@author
