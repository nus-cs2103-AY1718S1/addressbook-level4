package seedu.address.commons.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

//@@author eldriclim

/**
 * Utility methods for checking event overlaps
 */
public class DateTimeUtil {

    public static final DateTimeFormatter EVENT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public static String parseLocalDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(EVENT_DATETIME_FORMAT);
    }

    public static LocalDateTime parseStringToLocalDateTime(String input) throws DateTimeParseException {
        return LocalDateTime.parse(input, EVENT_DATETIME_FORMAT);
    }

    /**
     * Extracts duration of event if exist.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Duration parseDuration(String durationInput) throws IllegalValueException {

        if (durationInput.equals("")) {
            return Duration.ofMinutes(0);
        }

        String dayPattern = "(\\d+)d";
        String hourPattern = "(\\d+)h";
        String minPattern = "(\\d+)m";

        int dayCount = 0;
        int hourCount = 0;
        int minCount = 0;

        Pattern pattern = Pattern.compile(dayPattern);
        Matcher matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            dayCount = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile(hourPattern);
        matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            hourCount = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile(minPattern);
        matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            minCount = Integer.parseInt(matcher.group(1));
        }

        if (dayCount < 0 || hourCount < 0 || minCount < 0 || hourCount > 23 || minCount > 59) {
            throw new IllegalValueException("Illegal values detected.");
        }

        return Duration.ofMinutes(dayCount * 24 * 60 + hourCount * 60 + minCount);
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

    /**
     * Checks to see if date lies in between Event start time and end time.
     *
     * @param event
     * @param refDate
     * @return
     */
    public static boolean containsReferenceDate(Event event, LocalDate refDate) {
        LocalDate startDate = event.getEventTime().getStart().toLocalDate();
        LocalDate endDate = event.getEventTime().getEnd().toLocalDate();

        return
                startDate.isEqual(refDate)
                        || endDate.isEqual(refDate)
                        || (startDate.isBefore(refDate) && endDate.isAfter(refDate));
    }

}
