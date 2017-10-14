package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

import java.text.DateFormatSymbols;



/**
 * Represents a Lesson time slot in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSLot(String)}
 */
public class TimeSlot {
    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "Lesson time slot should contain 4 parts: " + ""
                    + "3 letters abbreviations for the week days, "
                    + " [4 digits 24-hour clock format "
                    + " - "
                    + " 4 digits 24-hour clock format]";
    public static final String TIMESLOT_VALIDATION_REGEX = "[a-zA-Z]{3}\\\\[[\\\\d]{4}-[\\\\d]{4}]";

    public final String value;

    /**
     * Validates given time slot.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public TimeSlot(String timeSlot) throws IllegalValueException {
        requireNonNull(timeSlot);
        String trimmedTimeSlot = timeSlot.trim();
        if (!isValidTimeSLot(trimmedTimeSlot)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }

        this.value = trimmedTimeSlot;
    }

    /**
     * Returns if a given string is a valid lesson time slot.
     */
    public static boolean isValidTimeSLot(String test) {
        if(test.matches(TIMESLOT_VALIDATION_REGEX)){
            String weekDay = test.substring(0, 3);
            String startHour = test.substring(4, 6);
            String startMin = test.substring(6, 8);
            String endHour = test.substring(9, 11);
            String endMin = test.substring(11, 13);
            boolean isWeekValid = weekValid(weekDay);
            boolean isTimeValid = timeValid(startHour,startMin,endHour,endMin);

            if(isWeekValid && isTimeValid) {
                return true;
            }
        }

        return false;
    }

    private static boolean weekValid(String weekStr) {
        String[] namesOfDays = DateFormatSymbols.getInstance().getShortWeekdays();
        for (int i = 0; i < namesOfDays.length; i++) {
            if (weekStr.equalsIgnoreCase(namesOfDays[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param sh start hour
     * @param sm start minute
     * @param et end hour
     * @param em end minute
     * @return true if the time matches the time format
     */
    private static boolean timeValid(String sh, String sm, String et, String em) {
        int startHr = Integer.parseInt(sh);
        int startMin = Integer.parseInt(sm);
        int endHr = Integer.parseInt(et);
        int endMin = Integer.parseInt(em);
        int startTemp = 0;
        int endTemp = -1;
        if (hourValid(startHr) && hourValid(endHr) && minValid(startMin) && minValid(endMin)) {
            startTemp = startHr * 100 + startMin;
            endTemp = endHr * 100 + endMin;
        }
        return startTemp < endTemp;
    }

    private static boolean hourValid(int hour) {
        return (hour <= 23 && hour >= 0);
    }

    private static boolean minValid(int minute) {
        return (minute <= 59 && minute >= 0);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeSlot // instanceof handles nulls
                && this.value.equals(((TimeSlot) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
