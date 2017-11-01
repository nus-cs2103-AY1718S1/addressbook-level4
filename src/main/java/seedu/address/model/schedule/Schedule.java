package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.TreeSet;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 */
public class Schedule {

    private TreeSet<Integer> busyTime;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Schedule() {
        busyTime = new TreeSet<Integer>();
    }
    public Schedule(TreeSet<Integer> busyTime) throws IllegalValueException {
        requireNonNull(busyTime);
        this.busyTime = busyTime;
    }

    /**
     * Add a slot of time with the unit of 30min based on the startTime.
     */
    public void addTime(Integer startTime) {
        if (!busyTime.contains(startTime)) {
            busyTime.add(startTime);
        }
    }

    /**
     * Clear a slot of time with the unit of 30min based on the startTime.
     */
    public void clearTime(Integer startTime) {
        if (busyTime.contains(startTime)) {
            busyTime.remove(startTime);
        }
    }

    @Override
    public String toString() {
        return busyTime.toString();
    }

    @Override
    public int hashCode() {
        return busyTime.hashCode();
    }

}
