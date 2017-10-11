package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of events that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class EventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Adds a event to the list.
     */
    public void add(ReadOnlyEvent toAdd) {
        requireNonNull(toAdd);
        internalList.add(new Event(toAdd));
    }

    //    /**
    //     * Replaces the person {@code target} in the list with {@code editedEvent}.
    //     *
    //     * @throws DuplicateEventException if the replacement is equivalent to another existing person in the list.
    //     * @throws EventNotFoundException  if {@code target} could not be found in the list.
    //     */
    //    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
    //            throws DuplicateEventException, EventNotFoundException {
    //        requireNonNull(editedEvent);
    //
    //        int index = internalList.indexOf(target);
    //        if (index == -1) {
    //            throw new EventNotFoundException();
    //        }
    //
    //        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
    //            throw new DuplicateEventException();
    //        }
    //
    //        internalList.set(index, new Event(editedEvent));
    //    }
    //
    //    /**
    //     * Removes the equivalent person from the list.
    //     *
    //     * @throws EventNotFoundException if no such person could be found in the list.
    //     */
    //    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
    //        requireNonNull(toRemove);
    //        final boolean personFoundAndDeleted = internalList.remove(toRemove);
    //        if (!personFoundAndDeleted) {
    //            throw new EventNotFoundException();
    //        }
    //        return personFoundAndDeleted;
    //    }
    //
    //    public void setEvents(UniqueEventList replacement) {
    //        this.internalList.setAll(replacement.internalList);
    //    }
    //
    //    public void setEvents(List<? extends ReadOnlyEvent> persons) throws DuplicateEventException {
    //        final UniqueEventList replacement = new UniqueEventList();
    //        for (final ReadOnlyEvent person : persons) {
    //            replacement.add(new Event(person));
    //        }
    //        setEvents(replacement);
    //    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && this.internalList.equals(((EventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
