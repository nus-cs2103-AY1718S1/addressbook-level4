package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author dalessr
/**
 * Indicates a request for opening github webview
 */
public class OpenGithubWebViewEvent extends BaseEvent {

    public OpenGithubWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
