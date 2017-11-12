package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author DarrenCzen
/**
 * Indicates a request to load the website of a person in the Browser.
 */
public class AccessWebsiteRequestEvent extends BaseEvent {

    public final String website;

    public AccessWebsiteRequestEvent(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
