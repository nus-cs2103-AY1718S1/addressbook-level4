package seedu.address.model.schedule;

import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 */
public class Schedule {


    public static final String MESSAGE_SCHEDULE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public TreeSet<Integer> busyTime;

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

    public void addTime(Integer startTime){
        if(!busyTime.contains(startTime)){
            busyTime.add(startTime);
        }
    }

    @Override
    public String toString() {
        return busyTime.toString();
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.schedule.equals(((schedule) other).value)); // state check
    }
     */

    @Override
    public int hashCode() {
        return busyTime.hashCode();
    }

}
