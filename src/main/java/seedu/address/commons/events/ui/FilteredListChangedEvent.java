package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lincredibleJC
/**
 * Represents a Change in the current Filtered List
 */
public class FilteredListChangedEvent extends BaseEvent {

    private final ObservableList<ReadOnlyPerson> currentFilteredList;

    public FilteredListChangedEvent(ObservableList<ReadOnlyPerson> currentFilteredList) {
        this.currentFilteredList = currentFilteredList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getCurrentFilteredList() {
        return currentFilteredList;
    }
}
