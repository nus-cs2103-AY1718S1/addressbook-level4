package seedu.address.model.schedule;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author YuchenHe98
/**
 * The object representing a day, from Monday to Sunday in a person's schedule.
 */
public class Day {

    private final Integer day;

    public Day(String dayToAdd) throws IllegalValueException {
        if ("Monday".equals(dayToAdd)) {
            day = 1;
        } else if ("Tuesday".equals(dayToAdd)) {
            day = 2;
        } else if ("Wednesday".equals(dayToAdd)) {
            day = 3;
        } else if ("Thursday".equals(dayToAdd)) {
            day = 4;
        } else if ("Friday".equals(dayToAdd)) {
            day = 5;
        } else if ("Saturday".equals(dayToAdd)) {
            day = 6;
        } else if ("Sunday".equals(dayToAdd)) {
            day = 7;
        } else {
            throw new IllegalValueException("Not a proper day");
        }
    }

    public boolean isValid() {
        return day >= 1 && day <= 7;
    }

    /**
     * Returns if the string represents a valid day.
     */
    public static boolean isValidDay(String test) {
        return "Monday".equals(test)
                || "Tuesday".equals(test)
                || "Wednesday".equals(test)
                || "Thursday".equals(test)
                || "Friday".equals(test)
                || "Saturday".equals(test)
                || "Sunday".equals(test);
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day) // instanceof handles nulls
                && (this.day == ((Day) other).day); // state check
    }
}
