package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** Indicates the AddressBook in the model has changed*/
public class ListEvent extends BaseEvent {

    public final ObservableList<ReadOnlyPerson> data;

    public ListEvent(ObservableList<ReadOnlyPerson> filteredPersonList) {
        this.data = filteredPersonList;
    }

    @Override
    public String toString() {
        return "Listing unfiltered persons";
    }
}
