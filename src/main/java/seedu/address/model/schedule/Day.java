package seedu.address.model.schedule;

import seedu.address.model.schedule.exceptions.NotValidTimeException;

public class Day {

    private final Integer day;

    public Day(String dayToAdd) {
        if(dayToAdd.equals("Monday")){
            day = 1;
        } else if(dayToAdd.equals("Tuesday")){
            day = 2;
        } else if(dayToAdd.equals("Wednesday")){
            day = 3;
        } else if(dayToAdd.equals("Thursday")){
            day = 4;
        } else if(dayToAdd.equals("Friday")){
            day = 5;
        } else if(dayToAdd.equals("Saturday")){
            day = 6;
        } else if(dayToAdd.equals("Sunday")){
            day = 7;
        } else {
            day = -1;
        }
    }

    public boolean isValid(){
        return (day != -1);
    }

    public Integer getDay() {
        return day;
    }
}
