package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.ReadOnlyPerson;

//@@author eldriclim
/**
 * When a selection is made in the EventListPanel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {

    private Event selectedEvent;

    public EventPanelSelectionChangedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getMemberAsArrayList() {

        return FXCollections.observableArrayList(selectedEvent.getMemberList().asReadOnlyMemberList());
    }

    public String getEventName() {
        return selectedEvent.getEventName().toString();
    }
}
