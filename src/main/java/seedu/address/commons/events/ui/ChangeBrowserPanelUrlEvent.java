package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author kenpaxtonlim
/**
 * Indicates a request to change the url on the browser panel.
 */
public class ChangeBrowserPanelUrlEvent extends BaseEvent {

    public final String url;

    public ChangeBrowserPanelUrlEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
