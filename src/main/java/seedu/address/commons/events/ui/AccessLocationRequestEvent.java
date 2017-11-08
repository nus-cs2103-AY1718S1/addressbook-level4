package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author DarrenCzen
/**
 * An event requesting to view the location of a person.
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
