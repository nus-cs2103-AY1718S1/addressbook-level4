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
 * Utility class for handling DateTime operations.
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
     * Returns a Duration object based on the durationInput.
     *
     * @param durationInput user input for duration
     * @return the parsed Duration object
     * @throws IllegalValueException if time does not conform to the proper standards
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
     * Returns true if there is an overlap between the given events.
     *
     * @param event1 event to compare
     * @param event2 event to compare against
     * @return true if overlap is detected
     */
    public static boolean checkEventClash(Event event1, Event event2) {

        if (event1.getEventTime().getStart().isEqual(event2.getEventTime().getStart())) {
            return true;
        }

        if (event1.getEventTime().getEnd().isEqual(event2.getEventTime().getEnd())) {
            return true;
        }

        if (isBetween(event1.getEventTime().getEnd(), event2)) {
            return true;
        }

        if (isBetween(event1.getEventTime().getStart(), event2)) {
            return true;
        }

        if (event1.getEventTime().getStart().isAfter(event2.getEventTime().getStart())
                && event1.getEventTime().getEnd().isBefore(event2.getEventTime().getEnd())) {
            return true;
        }

        if (event2.getEventTime().getStart().isAfter(event1.getEventTime().getStart())
                && event2.getEventTime().getEnd().isBefore(event1.getEventTime().getEnd())) {
            return true;
        }

        return false;

    }

    /**
     * Returns true if given time lies within the duration of an event.
     *
     * @param time to check against
     * @param event with a specified duration (start time & end time)
     * @return true if time is within event duration
     * @see Event
     */
    public static boolean isBetween(LocalDateTime time, Event event) {
        if (time.isAfter(event.getEventTime().getStart()) && time.isBefore(event.getEventTime().getEnd())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if duration of an event consist of given date.
     *
     * @param event the event to check
     * @param referenceDate the date to check against
     * @return true if date is within event duration
     */
    public static boolean containsReferenceDate(Event event, LocalDate referenceDate) {
        LocalDate startDate = event.getEventTime().getStart().toLocalDate();
        LocalDate endDate = event.getEventTime().getEnd().toLocalDate();

        return
                startDate.isEqual(referenceDate)
                        || endDate.isEqual(referenceDate)
                        || (startDate.isBefore(referenceDate) && endDate.isAfter(referenceDate));
    }

}
