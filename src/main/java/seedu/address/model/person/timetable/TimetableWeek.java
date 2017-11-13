package seedu.address.model.person.timetable;

import static seedu.address.commons.util.timetable.TimetableParserUtil.parseDay;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zacharytang
/**
 * Represents a full timetable for a week
 */
public class TimetableWeek {

    private static final int ARRAY_NUM_DAYS = 5;

    private TimetableDay[] days;

    public TimetableWeek() {
        days = new TimetableDay[ARRAY_NUM_DAYS];
        for (int i = 0; i < ARRAY_NUM_DAYS; i++) {
            days[i] = new TimetableDay();
        }
    }

    public boolean doesSlotHaveLesson(String day, String timing) throws IllegalValueException {
        return days[parseDay(day)].doesSlotHaveLesson(timing);
    }

    public void updateSlotsWithLesson(String day, String startTime, String endTime) throws IllegalValueException {
        days[parseDay(day)].updateSlotsWithLesson(startTime, endTime);
    }

    private TimetableDay getDay(int dayIndex) {
        return days[dayIndex];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimetableWeek)) {
            return false;
        }

        for (int i = 0; i < ARRAY_NUM_DAYS; i++) {
            if (!this.days[i].equals(((TimetableWeek) other).getDay(i))) {
                return false;
            }
        }

        return true;
    }
}
