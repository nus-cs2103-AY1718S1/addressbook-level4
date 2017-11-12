package seedu.room.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.model.event.Event;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.UniqueEventList;
import seedu.room.model.event.exceptions.DuplicateEventException;
import seedu.room.model.event.exceptions.EventNotFoundException;

//@@author sushinoya
/**
 * Wraps all data at the event-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventBook implements ReadOnlyEventBook {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventBook() {}

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// List Overwrite Operations

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        try {
            setEvents(newData.getEventList());
        } catch (DuplicateEventException e) {
            assert false : "EventBooks should not have duplicate events";
        }
    }

    //// Event-Level Operations

    /**
     * Adds an event to the event book.
     *
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *                                 another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);

        Event editedEvent = new Event(editedReadOnlyEvent);
        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code EventBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    //// Sort Event Book
    /**
     * Sorts the UniqueEventList, events.
     *
     * @throws AlreadySortedException if the list is already sorted by given criteria.
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {

        if (events.getCurrentlySortedBy().equals(sortCriteria)) {
            throw new AlreadySortedException("List already sorted by: " + sortCriteria);
        } else {
            events.sortBy(sortCriteria);
        }
    }


    //// Util Methods

    @Override
    public String toString() {
        return events.asObservableList().size() + " events";
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }

    public UniqueEventList getUniqueEventList() {
        return events;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventBook // instanceof handles nulls
                && this.events.equals(((EventBook) other).events));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events);
    }

}
