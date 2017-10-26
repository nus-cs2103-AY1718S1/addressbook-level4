package seedu.address.model.schedule;

import seedu.address.model.schedule.exceptions.NotValidTimeException;

public class Time {

    private final Integer EarliestTime = 0600;
    private final Integer LatestTime = 2330;

    private Integer time;

    public Time(String time) throws NotValidTimeException {
        this.time = Integer.parseInt(time);
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
}
