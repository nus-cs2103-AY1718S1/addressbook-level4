package seedu.address.model.schedule;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddScheduleCommand;

public class Day {

    private final Integer day;

    public Day(String dayToAdd) throws IllegalValueException {
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
            throw new IllegalValueException("Not a proper day");
        }
    }

    public boolean isValid(){
        return day >= 1 && day <= 7;
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day) // instanceof handles nulls
                && (this.day == ((Day) other).day); // state check
    }
}
