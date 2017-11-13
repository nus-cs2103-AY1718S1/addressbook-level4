package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ritchielq-reuse
/**
 * Indicates a request to change active tab for browser panel
 */
public class BrowserPanelChangeActiveTabEvent extends BaseEvent {

    public final int targetTab;

    public BrowserPanelChangeActiveTabEvent(int targetTab) {
        this.targetTab = targetTab;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
