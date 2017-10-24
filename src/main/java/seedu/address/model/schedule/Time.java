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
        return (this.time >= EarliestTime && this.time <= LatestTime);
    }
}
