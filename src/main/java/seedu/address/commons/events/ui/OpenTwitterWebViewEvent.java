package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dalessr
/**
 * Indicates a request for opening twitter webview
 */
public class OpenTwitterWebViewEvent extends BaseEvent {

    public OpenTwitterWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
