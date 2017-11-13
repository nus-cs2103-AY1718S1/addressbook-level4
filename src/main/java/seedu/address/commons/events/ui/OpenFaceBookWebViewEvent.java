package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dalessr
/**
 * Indicates a request for opening facebook webview
 */
public class OpenFaceBookWebViewEvent extends BaseEvent {

    public OpenFaceBookWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
