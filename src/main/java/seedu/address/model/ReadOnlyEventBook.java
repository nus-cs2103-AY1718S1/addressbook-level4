package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;

//@@author kaiyu92

/**
 * Unmodifiable view of an event book
 */
public interface ReadOnlyEventBook {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

}
