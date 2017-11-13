//@@author majunting
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for Direction search in browser panel
 */
public class BrowserPanelLocateEvent extends BaseEvent {

    private final String start;
    private final String end;

    public BrowserPanelLocateEvent(String start, String end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getEndAddress() {
        return end;
    }

    public String getStartAddress() {
        return start;
    }
}
