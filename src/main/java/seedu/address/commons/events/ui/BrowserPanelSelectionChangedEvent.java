package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class BrowserPanelSelectionChangedEvent extends BaseEvent {

    private final String browserSelection;

    public BrowserPanelSelectionChangedEvent(String browserSelection) {
        this.browserSelection = browserSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBrowserSelection() {
        return this.browserSelection;
    }
}
