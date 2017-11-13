package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Indicates a request to toggle the view to PersonPanel.
 */
public class ToggleSearchBoxStyle extends BaseEvent {

    private final boolean isPinnedStyle;

    public ToggleSearchBoxStyle(boolean isPinnedStyle) {
        this.isPinnedStyle = isPinnedStyle;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public boolean isPinnedStyle() {
        return isPinnedStyle;
    }
}
