package seedu.address.model.schedule;

import java.util.TreeSet;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * The object representing a slot representing a span of time when a person is busy.
 */
public class Slot {

    public final int dayCoefficent = 10000;

    private TreeSet<Integer> busyTime;

    public Slot(Day day, Time start, Time end) throws IllegalValueException {
        if (!start.isValid() || !end.isValid() || !day.isValid()) {
            throw new IllegalValueException("Time given is not valid!");
        }

        busyTime = new TreeSet<>();
        int timeNumber;
        for (timeNumber = start.getTime(); timeNumber < end.getTime(); timeNumber += 10) {
            if (timeNumber % 100 != 30 && timeNumber % 100 != 0) {
                continue;
            } else {
                busyTime.add(timeNumber + day.getDay() * dayCoefficent);
            }
        }
    }

    public TreeSet<Integer> getBusyTime() {
        return busyTime;
    }
}
