//@@author A0162268B
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * A list of events that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class EventList implements Iterable<Event> {

    private static final Logger logger = LogsCenter.getLogger(EventList.class);

    private final ObservableTreeMap<Timeslot, Event> internalMap = new
            ObservableTreeMap<>();
    // used by asObservableList()
    private final ObservableTreeMap<Timeslot, ReadOnlyEvent> mappedTreeMap =
            ObservableTreeMap.map(internalMap, (event) -> event);

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

        Event targetEvent = new Event(target);
        if (!internalMap.containsValue(targetEvent)) {
            throw new EventNotFoundException();
        }

        if (hasClashWith(new Event(editedEvent))) {
            throw new EventTimeClashException();
        }

        internalMap.remove(targetEvent.getTimeslot());
        internalMap.put(editedEvent.getTimeslot(), new Event(editedEvent));
    }

    /**
     * Removes the equivalent event from the tree map.
     *
     * @throws EventNotFoundException if no such person could be found in the tree map.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFound = internalMap.containsValue(toRemove);
        if (!eventFound) {
            throw new EventNotFoundException();
        }
        internalMap.remove(toRemove.getTimeslot());
        return eventFound;
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

    /**
     * Check if a given event has any time clash with any event in the EventList.
     * @param event for checking
     * @return true if there is a clashing event.
     */
    private boolean hasClashWith(Event event) {
        Iterator<Event> iterator = this.iterator();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if (e.clashesWith(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the backing tree map as an {@code ObservableTreeMap}.
     */
    public ObservableTreeMap<Timeslot, ReadOnlyEvent> asObservableTreeMap() {
        return mappedTreeMap;
    }

    /**
     * Returns the backing tree map as an {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        ObservableList<ReadOnlyEvent> list = FXCollections.observableList(new ArrayList<>(internalMap.values()));
//        logger.info("EventList --------- Got EventList with " + internalMap.size() + " events inside");
        return FXCollections.unmodifiableObservableList(list);
    }

    @Override
    public Iterator<Event> iterator() {
        Collection<Event> c = internalMap.values();
        return c.iterator();
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
