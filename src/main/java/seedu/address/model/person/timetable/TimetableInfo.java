package seedu.address.model.person.timetable;

import static seedu.address.commons.util.timetable.TimetableParserUtil.parseWeekType;
import static seedu.address.model.person.timetable.Timetable.WEEK_BOTH;
import static seedu.address.model.person.timetable.Timetable.WEEK_EVEN;
import static seedu.address.model.person.timetable.Timetable.WEEK_ODD;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zacharytang
/**
 * Fully represents all information about a person's timetable slots
 */
public class TimetableInfo {

    public static final int ARRAY_NUM_EVEN_ODD = 2;

    private TimetableWeek[] timetable;

    public TimetableInfo() {
        timetable = new TimetableWeek[ARRAY_NUM_EVEN_ODD];
        for (int i = 0; i < ARRAY_NUM_EVEN_ODD; i++) {
            timetable[i] = new TimetableWeek();
        }
    }

    /**
     * Checks if a specific slot has a lesson
     */
    public boolean doesSlotHaveLesson(String weekType, String day, String timing) throws IllegalValueException {
        int weekIndex = parseWeekType(weekType);
        if (weekIndex != WEEK_ODD && weekIndex != WEEK_EVEN) {
            throw new IllegalValueException("Please specify a week type!");
        }
        return timetable[weekIndex].doesSlotHaveLesson(day, timing);
    }

    /**
     * Updates the timetable using lesson information provided
     */
    public void updateSlotsWithLesson(String weekType, String day, String startTime, String endTime)
            throws IllegalValueException {
        int weekIndex = parseWeekType(weekType);
        if (weekIndex == WEEK_BOTH) {
            timetable[WEEK_ODD].updateSlotsWithLesson(day, startTime, endTime);
            timetable[WEEK_EVEN].updateSlotsWithLesson(day, startTime, endTime);
        } else {
            timetable[weekIndex].updateSlotsWithLesson(day, startTime, endTime);
        }
    }

    private TimetableWeek getWeek(int weekIndex) {
        return timetable[weekIndex];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimetableInfo)) {
            return false;
        }

        for (int i = 0; i < ARRAY_NUM_EVEN_ODD; i++) {
            if (!this.timetable[i].equals(((TimetableInfo) other).getWeek(i))) {
                return false;
            }
        }

        return true;
    }
}
