package seedu.address.model.person;

import static seedu.address.model.util.DateUtil.convertStringToDate;
import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;

//@@author lawwman
/**
 * Represents the date of when the Person was instantiated in the address book, i.e. the date
 * the Person borrows money.
 * Guarantees: immutable;
 */
public class DateBorrow {

    public final String value;

    public DateBorrow() {
        Date date = new Date();
        value = formatDate(date);
    }

    /**
     * Creates a copy of the DateBorrow object with a set date.
     * @param date must be a valid date
     */
    public DateBorrow(String date) {
        value = date;
    }

    public Date getDate() {
        return convertStringToDate(value);
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
