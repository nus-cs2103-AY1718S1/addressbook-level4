package seedu.address.model.schedule;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.TreeSet;

public class Slot {

    public final int DAY_COEFFICIENT = 10000;

    private TreeSet<Integer> busyTime;

    public Slot(Day day, Time start, Time end) throws IllegalValueException {
        if(!start.isValid() || !end.isValid() || !day.isValid()){
            throw new IllegalValueException("Time given is not valid!");
        }

        busyTime = new TreeSet<>();
        int timeNumber;
        for(timeNumber = start.getTime(); timeNumber < end.getTime(); timeNumber += 10){
            if(timeNumber % 100 != 30 && timeNumber % 100 != 0){
                continue;
            } else {
                busyTime.add(timeNumber + day.getDay() * DAY_COEFFICIENT);
            }
        }
    }

    public TreeSet<Integer> getBusyTime() {
        return busyTime;
    }
}
