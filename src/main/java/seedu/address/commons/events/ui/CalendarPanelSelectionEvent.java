package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Calendar Panel
 */

public class CalendarPanelSelectionEvent extends BaseEvent {

    private final String newSelection;

    public CalendarPanelSelectionEvent (String newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
