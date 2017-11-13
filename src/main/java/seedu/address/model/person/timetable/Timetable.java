package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.timetable.TimetableParserUtil.parseUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zacharytang
/**
 * Represents a person's timetable in the address book
 * Guarantees: Immutable
 */
public class Timetable {

    public static final int WEEK_ODD = 0;
    public static final int WEEK_EVEN = 1;
    public static final int WEEK_BOTH = -1;

    public static final int DAY_MONDAY = 0;
    public static final int DAY_TUESDAY = 1;
    public static final int DAY_WEDNESDAY = 2;
    public static final int DAY_THURSDAY = 3;
    public static final int DAY_FRIDAY = 4;

    public static final String MESSAGE_TIMETABLE_URL_CONSTRAINTS =
            "Timetable URLs should be a valid shortened NUSMods URL";
    public static final String MESSAGE_INVALID_SHORT_URL = "Invalid shortened URL provided";
    public static final String EMPTY_TIMETABLE_STRING = "";

    public static final String[] ARRAY_DAYS = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday"
    };

    public static final String[] ARRAY_WEEKS = {
        "Odd Week",
        "Even Week"
    };

    public static final String[] ARRAY_TIMES = {
        "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130",
        "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530",
        "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930",
        "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"
    };

    private static final String NUSMODS_SHORT = "modsn.us";
    private static final String URL_HOST_REGEX = "\\/\\/.*?\\/";

    public final String value;

    private final TimetableInfo timetable;

    public Timetable(String url) throws IllegalValueException {
        requireNonNull(url);
        String trimmedUrl = url.trim();

        // If no url provided, returns an empty timetable
        if (trimmedUrl.equals(EMPTY_TIMETABLE_STRING)) {
            this.value = EMPTY_TIMETABLE_STRING;
            this.timetable = new TimetableInfo();
            return;
        }

        if (!isValidUrl(trimmedUrl)) {
            throw new IllegalValueException(MESSAGE_TIMETABLE_URL_CONSTRAINTS);
        }

        this.value = trimmedUrl;
        this.timetable = parseUrl(trimmedUrl);
    }

    /**
     * Returns if a url is a valid NUSMods url
     */
    public static boolean isValidUrl(String test) {
        Matcher matcher = Pattern.compile(URL_HOST_REGEX).matcher(test);
        if (!matcher.find()) {
            return false;
        }

        String hostName = matcher.group()
                .substring(2, matcher.group().length() - 1);

        return hostName.equals(NUSMODS_SHORT);
    }

    /**
     * Checks if a timeslot specified has a lesson
     */
    public boolean doesSlotHaveLesson(String weekType, String day, String timing) throws IllegalValueException {
        return timetable.doesSlotHaveLesson(weekType, day, timing);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timetable // instanceof handles nulls
                && this.value.equals(((Timetable) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
