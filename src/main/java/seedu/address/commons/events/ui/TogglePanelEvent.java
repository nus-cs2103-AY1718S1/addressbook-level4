package seedu.address.commons.events.ui;

//@@author DarrenCzen

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to toggle the list panel
 */
public class TogglePanelEvent extends BaseEvent {

    public final String selectedPanel;

    public TogglePanelEvent(String selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
