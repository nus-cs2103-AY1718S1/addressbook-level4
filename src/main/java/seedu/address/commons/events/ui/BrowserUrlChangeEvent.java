package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author alexfoodw
/**
 * Indicates a change in browser page
 */
public class BrowserUrlChangeEvent extends BaseEvent {
    private String processType;

    public BrowserUrlChangeEvent(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
