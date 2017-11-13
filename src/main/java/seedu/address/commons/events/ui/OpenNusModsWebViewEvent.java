package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dalessr
/**
 * Indicates a request for opening nusmods webview
 */
public class OpenNusModsWebViewEvent extends BaseEvent {

    public OpenNusModsWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
