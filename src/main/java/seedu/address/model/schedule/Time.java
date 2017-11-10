package seedu.address.model.schedule;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author YuchenHe98
/**
 * The object representing the time of the start of a 30-minute-span when a person is free.
 */
public class Time {

    private final Integer earliestTime = 600;
    private final Integer latestTime = 2330;

    private Integer time;

    public Time(String time) throws IllegalValueException {
        this.time = Integer.parseInt(time);
        if (!isValid()) {
            throw new IllegalValueException("Not a proper time form");
        }
    }


    /**
     * Returns if the time input is a valid schedule time.
     */
    public boolean isValid() {
        //As the string representing the time is no more accurate than 30 min, the ast two digits must be 30 or 00.
        if (time % 100 != 0 && time % 100 != 30) {
            return false;
        }
        return (this.time >= earliestTime && this.time <= latestTime);
    }

    public Integer getTime() {
        return time;
    }

    /**
     * Returns if the time input is a valid schedule time.
     */
    public static boolean isValidTime(String test) {
        if (test.length() != 4) {
            return false;
        }
        char[] toTest = test.toCharArray();
        for (int i = 0; i < toTest.length; i++) {
            if (!Character.isDigit(toTest[i])) {
                return false;
            }
        }
        int timeNumber = Integer.parseInt(test);
        if (timeNumber > 2330 || timeNumber < 600) {
            return false;
        }
        if (timeNumber % 100 != 0 && timeNumber % 100 != 30) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time) // instanceof handles nulls
                && (this.time.equals(((Time) other).time)); // state check
    }

    public static String getTimeToString(Integer time) {
        String toShow;
        if (time < 1000) {
            toShow = "0" + time;
        } else {
            toShow = "" + time;
        }
        return toShow;
    }

    /**
     * Next time integer. This method is only to be used in visualizing and arraging where exceptions are already
     * thrown so there is no need to check the format.
     */
    public static Integer increaseTimeInteger(Integer timeInteger) {
        if (timeInteger % 100 == 30) {
            int newTime = timeInteger + 70;
            return newTime;
        } else {
            int newTime = timeInteger + 30;
            return newTime;
        }
    }
}
