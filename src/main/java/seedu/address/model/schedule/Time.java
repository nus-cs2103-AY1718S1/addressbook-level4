package seedu.address.model.schedule;

import seedu.address.commons.exceptions.IllegalValueException;

public class Time {

    private final Integer EarliestTime = 0600;
    private final Integer LatestTime = 2330;

    private Integer time;

    public Time(String time) throws IllegalValueException {
        this.time = Integer.parseInt(time);
        if(!isValid()){
            throw new IllegalValueException("Not a proper time form");
        }
    }

    public boolean isValid(){
        //As the string representing the time is no more accurate than 30 min, the ast two digits must be 30 or 00.
        if(time % 100 != 0 && time % 100 != 30){
            return false;
        }
        return (this.time >= EarliestTime && this.time <= LatestTime);
    }

    public Integer getTime() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time) // instanceof handles nulls
                && (this.time == ((Time) other).time); // state check
    }
}
