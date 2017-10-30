package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates that the colour has been changed.
 */
public class NewPersonListEvent extends BaseEvent {

    private ObservableList<ReadOnlyPerson> persons;

    public NewPersonListEvent(ObservableList<ReadOnlyPerson> persons) {
        this.persons = persons;
    }

    public ObservableList<ReadOnlyPerson> getPersonsList() {
        return persons;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
