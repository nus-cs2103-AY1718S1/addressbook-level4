package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author DarrenCzen
/**
 * Indicates a request to load the location of a person on Google Maps Search in the Browser.
 */
public class AccessLocationRequestEvent extends BaseEvent {
    public final String location;

    public AccessLocationRequestEvent(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
