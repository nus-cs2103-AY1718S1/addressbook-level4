package seedu.address.model.task;

import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

/**
 * Represents a date for a task which can be formatted.
 */
public class TaskDates {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates can only contain a String in the format dd-MM-yyyy";

    /**
     * Formats the first date of a given DateGroup into a String formatted for display.
     */
    public static String formatDate(DateGroup dates) throws IllegalValueException {
        Date date = dates.getDates().get(0);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy");
        return sdf.format(date);
    }

    /**
     * Returns true if the {@code startDate} is before the {@code deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(ReadOnlyTask task) {
        if (task.getStartDate().isEmpty() | task.getDeadline().isEmpty()) {
            return true;
        }
        Date dateone = new Date();
        Date datetwo = new Date();
        System.out.println(dateone.after(datetwo));
        return true;
       // if (task.startDateProperty().)
    }
}
