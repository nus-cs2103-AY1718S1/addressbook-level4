//@@author A0162268B
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.fxmisc.easybind.EasyBind;

import com.sun.tools.corba.se.idl.constExpr.Times;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * A list of events that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class EventList implements Iterable<Event> {

    private final ObservableTreeMap<Timeslot, Event> internalMap = new
            ObservableTreeMap<>();
    // used by asObservableList()
    private final ObservableTreeMap<Timeslot, ReadOnlyEvent> mappedTreeMap =
            EasyBind.map(internalMap, (event) -> event);

    /**
     * Adds a event to the list.
     */
    public void add(ReadOnlyEvent toAdd) {
        requireNonNull(toAdd);
        Event event = new Event(toAdd);
        internalMap.put(event.getTimeslot(), event);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws EventNotFoundException {
        requireNonNull(editedEvent);

        if (internalMap.containsValue(target)) {
            throw new EventNotFoundException();
        }

        internalMap.remove(target.getTimeslot());
        internalMap.put(editedEvent.getTimeslot(), editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such person could be found in the list.
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

    public void setEvents(List<? extends ReadOnlyEvent> persons) {
        final EventList replacement = new EventList();
        for (final ReadOnlyEvent person : persons) {
            replacement.add(new Event(person));
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableTreeMap}.
     */
    public ObservableTreeMap<Timeslot, ReadOnlyEvent> asObservableTreeMap() {
        return mappedTreeMap;
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
