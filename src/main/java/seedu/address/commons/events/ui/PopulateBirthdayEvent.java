package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates a change in any birthday value for any contacts
 */
public class PopulateBirthdayEvent extends BaseEvent {

    public final ObservableList<ReadOnlyPerson> contactList;

    public PopulateBirthdayEvent(ObservableList<ReadOnlyPerson> contactList) {
        this.contactList = contactList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
