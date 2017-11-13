package seedu.address.commons.events.ui;

//@@author jeffreygohkw
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class BrowserPanelLocateEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public BrowserPanelLocateEvent(ReadOnlyPerson person) {
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
