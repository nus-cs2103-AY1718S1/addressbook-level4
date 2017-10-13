package seedu.address.model.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRepaid {

    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";
    public static final String SIMPLE_DATE_FORMAT = "MM/DD/YY";

    public final String value;

    public DateRepaid() {
        DateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        Date date = null;
        try {
            date = formatter.parse("00/00/00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        value = ft.format(date);
    }

    /**
     * Creates a copy of the DateRepaid object with a set date.
     * @param date
     */
    public DateRepaid(String date) {
        value = date;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateRepaid // instanceof handles nulls
                && this.value.equals(((DateRepaid) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
