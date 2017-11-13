package seedu.address.commons.events.ui;

//@@author jeffreygohkw

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Location;

/**
 * Represents a selection change in the Person List Panel
 */
public class BrowserPanelNavigateEvent extends BaseEvent {

    private final Location fromLocation;
    private final Location toLocation;

    public BrowserPanelNavigateEvent(Location fromLocation, Location toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }
}
