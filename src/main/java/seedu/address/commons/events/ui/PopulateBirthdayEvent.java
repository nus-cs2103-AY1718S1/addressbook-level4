package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

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
