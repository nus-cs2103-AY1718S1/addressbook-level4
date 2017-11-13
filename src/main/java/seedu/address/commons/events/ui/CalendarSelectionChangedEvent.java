package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author eldriclim
/**
 * Represents a selection change in the Calendar view
 */
public class CalendarSelectionChangedEvent extends BaseEvent {

    private LocalDate selectedDate;

    public CalendarSelectionChangedEvent(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
