package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Toggles the sort by label every time list is sorted.
 */
public class ToggleSortByLabelEvent extends BaseEvent {

    public final String sortBy;

    public ToggleSortByLabelEvent(String input) {
        this.sortBy = input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @Override
    public String toString() {
        return this.sortBy;
    }
}
