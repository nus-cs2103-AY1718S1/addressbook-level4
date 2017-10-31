package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dalessr
/**
 * Represents a display of route from start location to end location in the Browser Panel
 */
public class BrowserPanelFindRouteEvent extends BaseEvent {

    private final ReadOnlyPerson person;
    private final String address;

    public BrowserPanelFindRouteEvent(ReadOnlyPerson person, String address) {
        this.person = person;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getSelectedPerson() {
        return person;
    }

    public String getAddress() {
        return address;
    }
}
