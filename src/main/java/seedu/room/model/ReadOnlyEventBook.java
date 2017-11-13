package seedu.room.model;

import javafx.collections.ObservableList;
import seedu.room.model.event.ReadOnlyEvent;

//@@author sushinoya
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
