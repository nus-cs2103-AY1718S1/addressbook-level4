package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's Timing in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class Timing {
    public static final String MESSAGE_TIMING_CONSTRAINTS =
            "Event Timings should contain a 4-digit 24-hour format start timing and end timing separated by a \"-\", "
                    + "and it should not be blank";
    /*
     * The first character of the Timing must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIMING_VALIDATION_REGEX =
            "(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]-(0[1-9]|1[0-9]|2[0-3])[0-5][0-9]";

    public final String timing;
    public final int start;
    public final int end;

    /**
     * Validates given Timing.
     *
     * @throws IllegalValueException if given Timing string is invalid.
     */
    public Timing(String timing) throws IllegalValueException {
        requireNonNull(timing);
        String trimmedTiming = timing.trim();

        String[] timings = trimmedTiming.split("-");
        String start = timings[0];
        String end = timings[1];

        int startTiming = Integer.parseInt(start);
        int endTiming = Integer.parseInt(end);

        if (!isValidTiming(trimmedTiming) || !isValidTimingInterval(startTiming, endTiming)) {
            throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
        }
        this.timing = trimmedTiming;
        this.start = startTiming;
        this.end = endTiming;
    }

    /**
     * Returns true if a given string is a valid event's Timing.
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TIMING_VALIDATION_REGEX);
    }

    /**
     * Returns true if start timing and end timing forms a valid 24-hour interval.
     *
     * @param start starting time
     * @param end   ending time
     * @return if timing is a valid 24-hour interval
     */
    public static boolean isValidTimingInterval(int start, int end) {
        if (start > end) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public String toString() {
        return timing;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timing // instanceof handles nulls
                && this.timing.equals(((Timing) other).timing)); // state check
    }

    @Override
    public int hashCode() {
        return timing.hashCode();
    }
}
