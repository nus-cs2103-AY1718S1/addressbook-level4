package seedu.address.model.person;

import java.text.SimpleDateFormat;
import java.util.Date;

//@@author lawwman

/**
 * Represents the date of when the Person was instantiated in the address book, i.e. the date
 * the Person borrows money.
 * Guarantees: immutable;
 */
public class DateBorrow {

    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";

    public final String value;

    public DateBorrow() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        value = ft.format(date);
    }

    /**
     * Creates a copy of the DateBorrow object with a set date.
     * @param date
     */
    public DateBorrow(String date) {
        value = date;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateBorrow // instanceof handles nulls
                && this.value.equals(((DateBorrow) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
