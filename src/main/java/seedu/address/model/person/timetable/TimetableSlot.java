package seedu.address.model.person.timetable;

//@@author zacharytang
/**
 * Represents a single slot in a timetable
 */
public class TimetableSlot {

    private boolean hasLesson;

    public TimetableSlot() {
        this.hasLesson = false;
    }

    public void setLesson() {
        hasLesson = true;
    }

    public boolean hasLesson() {
        return hasLesson;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimetableSlot // instanceof handles nulls
                && this.hasLesson == ((TimetableSlot) other).hasLesson());
    }
}
