package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lincredibleJC
/**
 * Represents a Change in the current FilteredPersonList
 */
public class FilteredPersonListChangedEvent extends BaseEvent {

    private final ObservableList<ReadOnlyPerson> currentFilteredList;

    public FilteredPersonListChangedEvent(ObservableList<ReadOnlyPerson> currentFilteredList) {
        this.currentFilteredList = currentFilteredList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getCurrentFilteredPersonList() {
        return currentFilteredList;
    }
}
