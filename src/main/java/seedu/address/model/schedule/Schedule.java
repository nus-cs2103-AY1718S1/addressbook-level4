package seedu.address.model.schedule;

import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

import apple.laf.JRSUIUtils;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Schedule {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public TreeSet<Integer> schedule;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */

    public Schedule() {
        schedule = new TreeSet<Integer>();
    }
    public Schedule(TreeSet<Integer> schedule) throws IllegalValueException {
        requireNonNull(schedule);
        this.schedule = schedule;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    /*public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }
     */

    @Override
    public String toString() {
        return schedule.toString();
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
        return schedule.hashCode();
    }

}
