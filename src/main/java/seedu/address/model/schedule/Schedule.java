package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.TreeSet;

import seedu.address.commons.core.PossibleDays;
import seedu.address.commons.exceptions.IllegalValueException;

//@@author YuchenHe98
/**
 * Represents a Person's schedule in the address book.
 */
public class Schedule {

    private TreeSet<Integer> timeSet;

    /**
     * Validates given schedule
     *
     * @throws IllegalValueException if given timeSet is invalid.
     */
    public Schedule() {
        timeSet = new TreeSet<Integer>();
    }
    public Schedule(TreeSet<Integer> timeSet) throws IllegalValueException {
        requireNonNull(timeSet);
        if (!isValidTimeSet(timeSet)) {
            throw new IllegalValueException("Should not use this constructor as invalid values are passed. ");
        }
        this.timeSet = timeSet;
    }

    /**
     * Add a slot of time with the unit of 30min based on the startTime.
     */
    public void addTime(Integer startTime) {
        if (!timeSet.contains(startTime)) {
            timeSet.add(startTime);
        }
    }

    /**
     * Clear a slot of time with the unit of 30min based on the startTime.
     */
    public void clearTime(Integer startTime) {
        if (timeSet.contains(startTime)) {
            timeSet.remove(startTime);
        }
    }

    @Override
    public String toString() {
        return timeSet.toString();
    }

    public TreeSet<Integer> getTimeSet() {
        return timeSet;
    }

    public boolean containsTimeNumber(Integer timeNumber) {
        return timeSet.contains(timeNumber);
    }

    @Override
    public int hashCode() {
        return timeSet.hashCode();
    }

    /**
     * Split the time set into seven sets according to the days.
     */
    public TreeSet<Integer>[] splitScheduleToDays() {
        TreeSet<Integer>[] timeSetArray = new TreeSet[7];
        for (int i = 0; i < 7; i++) {
            timeSetArray[i] = new TreeSet<>();
        }
        for (Integer time : this.timeSet) {
            int day = (time - time % PossibleDays.DAY_COEFFICIENT) / PossibleDays.DAY_COEFFICIENT;
            timeSetArray[day - 1].add(time % PossibleDays.DAY_COEFFICIENT);
        }
        return timeSetArray;
    }

    /**
     * Split the time set into seven sets according to the days given a time set.
     */
    public static TreeSet<Integer>[] splitScheduleToDays(TreeSet<Integer> toBeSplitted) {
        TreeSet<Integer>[] timeSetArray = new TreeSet[7];
        for (int i = 0; i < 7; i++) {
            timeSetArray[i] = new TreeSet<>();
        }
        for (Integer time : toBeSplitted) {
            int day = (time - time % PossibleDays.DAY_COEFFICIENT) / PossibleDays.DAY_COEFFICIENT;
            timeSetArray[day - 1].add(time % PossibleDays.DAY_COEFFICIENT);
        }
        return timeSetArray;
    }

    /**
     * Returns if the set input containing day and time info is a valid set.
     */
    public static boolean isValidTimeSet(TreeSet<Integer> setOfTime) {
        for (Integer timeNumber : setOfTime) {
            if (!isValidTimeNumber(timeNumber)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if the integer input containing day and time info is a valid schedule time.
     */
    private static boolean isValidTimeNumber(Integer timeNumber) {
        Integer temp = timeNumber;
        temp -= temp % PossibleDays.DAY_COEFFICIENT;
        if (temp < 1 * PossibleDays.DAY_COEFFICIENT || temp > 7 * PossibleDays.DAY_COEFFICIENT) {
            return false;
        }
        Integer timeInDay = timeNumber % PossibleDays.DAY_COEFFICIENT;
        String timeString;
        if (timeInDay < 1000) {
            timeString = "0" + timeInDay;
        } else {
            timeString = timeInDay + "";
        }
        if (Time.isValidTime(timeString)) {
            return true;
        } else {
            return false;
        }
    }
}
