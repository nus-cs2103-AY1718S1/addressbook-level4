//@@author A0162268B
package seedu.address.model.event.timeslot;

/**
 * Timeslot contains date and time and uses it as a comparable
 */
public class Timeslot {
    private Date date;
    private Timing timing;

    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "Event timings should contain a 6-digit date specifying date, month and year (in the format of dd/mm/yyyy) "
                    + "followed by a 4-digit 24-hour format start timing and end timing separated by a \"-\", "
                    + "and it should not be blank";

    public static final String TIMESLOT_VALIDATION_REGEX =
            "[0-2][0-9]/(0[1-9]|1[0-2])/[0-9][0-9][0-9][0-9] " +
                    "(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]-(0[1-9]|1[0-9]|2[0-3])[0-5][0-9]";

    /**
     * Returns true if a given string is a valid event's Timing.
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TIMESLOT_VALIDATION_REGEX);
    }
}
