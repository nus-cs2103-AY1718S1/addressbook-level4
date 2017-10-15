package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.DateParseException;


/**
 * Represents a Person's address in the address book.
 * Guarantees: Date is valid
 */
public class DateAdded {

    public static final String MESSAGE_DATEADDED_CONSTRAINTS =
            "DateAdded is in DD-MM-YYYY format, and it should not be blank";
    public static final String MESSAGE_DATE_PARSE_ERROR =
            "Error parsing from xml to Date object.";
    public static final String dateFormat = "dd/MM/yyyy hh:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(dateFormat);

    public final Date dateAdded;


    public DateAdded() {
        dateAdded = new Date();
    }

    public DateAdded(String dateAddedString) throws IllegalValueException {
        requireNonNull(dateAddedString);

        try {
            dateAdded = format.parse(dateAddedString);
        } catch (ParseException e) {
            throw new DateParseException(MESSAGE_DATE_PARSE_ERROR);
        }

    }

    @Override
    public String toString() {
        return format.format(dateAdded);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && (this.dateAdded.getTime() == (((DateAdded) other).dateAdded.getTime()))); // state check
    }

    @Override
    public int hashCode() {
        return dateAdded.hashCode();
    }

}
