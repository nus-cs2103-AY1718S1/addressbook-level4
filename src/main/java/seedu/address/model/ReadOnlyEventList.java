package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Unmodifiable view of an event list
 */
public interface ReadOnlyEventList {
    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();
}
