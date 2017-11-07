package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author eldriclim
/**
 * When a selection is made in the calendar
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
