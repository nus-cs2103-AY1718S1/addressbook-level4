package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dalessr
/**
 * Represents a display of location in the Browser Panel
 */
public class BrowserPanelShowLocationEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public BrowserPanelShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return person;
    }
}
