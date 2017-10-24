package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.event.Event;

/**
 * Utility methods for checking event overlaps
 */
public class DateTimeUtil {

    public static final DateTimeFormatter EVENT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String parseLocalDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(EVENT_DATETIME_FORMAT);
    }

    public static LocalDateTime parseStringToLocalDateTime(String input) {
        return LocalDateTime.parse(input, EVENT_DATETIME_FORMAT);
    }


    /**
     * Checks if two events on a timeline overlaps
     *
     * @param e1 event to compare
     * @param e2 event to compare against
     * @return true if overlap is detected; else return false
     */
    public static boolean checkEventClash(Event e1, Event e2) {

        if (e1.getEventTime().getStart().isEqual(e2.getEventTime().getStart())) {
            return true;
        }

        if (e1.getEventTime().getEnd().isEqual(e2.getEventTime().getEnd())) {
            return true;
        }

        if (isBetween(e1.getEventTime().getEnd(), e2)) {
            return true;
        }

        if (isBetween(e1.getEventTime().getStart(), e2)) {
            return true;
        }

        if (e1.getEventTime().getStart().isAfter(e2.getEventTime().getStart())
                && e1.getEventTime().getEnd().isBefore(e2.getEventTime().getEnd())) {
            return true;
        }

        if (e2.getEventTime().getStart().isAfter(e1.getEventTime().getStart())
                && e2.getEventTime().getEnd().isBefore(e1.getEventTime().getEnd())) {
            return true;
        }

        return false;

    }

    /**
     * Checks if a given time lines between an event
     *
     * @param t1 a given time
     * @param e1 an event with a specified duration (start time & end time)
     * @return true if t1 lines within e1; else returns false
     */
    public static boolean isBetween(LocalDateTime t1, Event e1) {
        if (t1.isAfter(e1.getEventTime().getStart()) && t1.isBefore(e1.getEventTime().getEnd())) {
            return true;
        } else {
            return false;
        }
    }
}
