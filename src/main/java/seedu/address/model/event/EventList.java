
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Date;
import seedu.address.model.event.timeslot.Timeslot;

//@@author reginleiff
/**
 * A list of events that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class EventList implements Iterable<Event> {

    private static final String MIDNIGHT_HOURS = " 0000-0001";

    private static final Logger logger = LogsCenter.getLogger(EventList.class);

    private final ObservableTreeMap<Timeslot, Event> internalMap = new
            ObservableTreeMap<>();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = FXCollections.observableArrayList(new
            ArrayList<>(internalMap.values()));

    public EventList() {
        internalMap.addListener((MapChangeListener.Change<? extends Timeslot, ? extends Event> change) -> {
            logger.info("Change heard.");
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                if (removed) {
                    mappedList.remove(change.getValueRemoved());
                } else {
                    mappedList.add(change.getValueAdded());
                }
            }
        });
    }
    /**
     * Adds a event to the tree map.
     */
    public void add(ReadOnlyEvent toAdd) throws EventTimeClashException {
        requireNonNull(toAdd);
        if (hasClashWith(new Event(toAdd))) {
            throw new EventTimeClashException();
        }
        internalMap.put(toAdd.getTimeslot(), new Event(toAdd));
    }

    /**
     * Replaces the event {@code target} in the tree map with {@code editedEvent}.
     *
     * @throws EventNotFoundException if {@code target} could not be found in the tree map.
     */
    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws EventNotFoundException, EventTimeClashException {
        requireNonNull(editedEvent);

        //@@author a0107442n
        Event targetEvent = new Event(target);
        if (!internalMap.containsValue(targetEvent)) {
            throw new EventNotFoundException();
        }


        if (hasClashWith(new Event(editedEvent))) {
            throw new EventTimeClashException();
        }
        internalMap.remove(targetEvent.getTimeslot());
        internalMap.put(editedEvent.getTimeslot(), new Event(editedEvent));
        //@@author
    }

    /**
     * Removes the equivalent event from the tree map.
     *
     * @throws EventNotFoundException if no such person could be found in the tree map.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        //@@author a0107442
        final boolean eventFound = internalMap.containsValue(toRemove);
        if (!eventFound) {
            throw new EventNotFoundException();
        }
        internalMap.remove(toRemove.getTimeslot());
        return eventFound;
        //@@author
    }

    public void setEvents(EventList replacement) {
        this.internalMap.putAll(replacement.internalMap);
    }

    public void setEvents(List<? extends ReadOnlyEvent> events) throws EventTimeClashException {
        final EventList replacement = new EventList();
        for (final ReadOnlyEvent event : events) {
            try {
                replacement.add(new Event(event));
            } catch (EventTimeClashException e) {
                throw e;
            }
        }
        setEvents(replacement);
    }

    //@@author a0107442n
    /**
     * Check if a given event has any time clash with any event in the EventList.
     * @param event for checking
     * @return true if there is a clashing event.
     */
    private boolean hasClashWith(Event event) {
        Iterator<Event> iterator = this.iterator();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if (e.clashesWith(event)
                    && (!e.getTitle().equals(event.getTitle())
                    || !e.getDescription().equals(event.getDescription()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the backing tree map as an {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        ObservableList<ReadOnlyEvent> list = FXCollections.observableList(new ArrayList<>(internalMap.values()));
        //logger.info("EventList ------------- got " + mappedList.size() + " list.");
        return FXCollections.unmodifiableObservableList(list);
    }

    @Override
    public Iterator<Event> iterator() {
        Collection<Event> c = internalMap.values();
        return c.iterator();
    }

    //@@author


    /**
     * Returns all the events on a particular date as an {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> getObservableSubList(Date date) {

        String startTimeslot = date.toString() + MIDNIGHT_HOURS;
        String endTimeslot = date.addDays(1).toString() + MIDNIGHT_HOURS;

        try {
            Timeslot start = new Timeslot(startTimeslot);
            Timeslot end = new Timeslot(endTimeslot);
            SortedMap<Timeslot, Event> sublist = internalMap.subMap(start, end);
            return FXCollections.observableList(new ArrayList<>(sublist.values()));
        } catch (IllegalValueException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && this.internalMap.equals(((EventList) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
}
