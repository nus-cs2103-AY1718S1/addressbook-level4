//@@author danielbrzn
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show a URL in the BrowserPanel.
 */
public class ShowUrlEvent extends BaseEvent {

    private final String link;

    public ShowUrlEvent(String link) {
        this.link = link;
    }

    public String getUrl() {
        return link;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
