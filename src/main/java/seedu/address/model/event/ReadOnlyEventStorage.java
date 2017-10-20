package seedu.address.model.event;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an event storage
 */
public interface ReadOnlyEventStorage {
    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

}