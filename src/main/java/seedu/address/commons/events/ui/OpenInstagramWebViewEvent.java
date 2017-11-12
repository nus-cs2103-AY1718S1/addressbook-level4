package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dalessr
/**
 * Indicates a request for opening instagram webview
 */
public class OpenInstagramWebViewEvent extends BaseEvent {

    public OpenInstagramWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
