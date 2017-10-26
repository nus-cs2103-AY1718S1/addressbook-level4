package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Parcel's delivery date in the address book.
 * Guarantees: mutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DeliveryDate {


    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Delivery dates should be in the format dd-mm-yyyy";
    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)" +
            "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?" +
            ":1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[" +
            "1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;
    public final Date date;
    public static final List<String> formatStrings = Arrays.asList("dd-MM-yyyy", "d-MM-yyyy", "d-M-yyyy", "dd-M-yyyy",
                                                                   "dd/MM/yyyy", "d/MM/yyyy", "d/M/yyyy", "dd/M/yyyy",
                                                                   "dd.MM.yyyy", "d.MM.yyyy", "d.M.yyyy", "dd/M.yyyy");

    /**
     * Validates given delivery date.
     *
     * @throws IllegalValueException if given delivery date string is invalid.
     */
    public DeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDate = deliveryDate.trim();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (isValidDate(trimmedDate)) {
            try {
                this.date = formatDate(trimmedDate);
                this.value = df.format(this.date);
            } catch (ParseException e) {
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
            }
        } else {
            throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }

    }

    private static Date formatDate(String inputDate) throws ParseException {
        for (String formatString : formatStrings)
        {
            return new SimpleDateFormat(formatString).parse(inputDate);
        }

        throw new ParseException(inputDate, 0);
    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
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
