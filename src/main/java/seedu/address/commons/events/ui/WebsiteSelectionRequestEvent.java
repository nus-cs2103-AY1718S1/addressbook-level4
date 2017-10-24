package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class WebsiteSelectionRequestEvent extends BaseEvent {


    private final String websiteRequested;

    public WebsiteSelectionRequestEvent(String websiteRequested) {
        this.websiteRequested = websiteRequested;
    }

    public String getWebsiteRequested() {
        return websiteRequested;
    }

    @Override
    public String toString() {
        return this.websiteRequested;
    }
}
