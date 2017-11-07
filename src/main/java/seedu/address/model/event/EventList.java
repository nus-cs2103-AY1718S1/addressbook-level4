//@@author erik0704
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * // @see CollectionUtil#elementsAreUnique(Collection)
 */
public class EventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<Event> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     * @throws DuplicateEventException
     * if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));
        Collections.sort(internalList);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing person in the list.
     * TODO: @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    public void setEvent(Event target, Event editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedEvent);

        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, new Event(editedEvent));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * TODO : @throws PersonNotFoundException if no such event could be found in the list.
     */
    public boolean remove(Event toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);

        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;

    }

    public void setEvents(EventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<? extends Event> events) throws DuplicateEventException {
        final EventList replacement = new EventList();
        for (final Event person : events) {
            replacement.add(new Event(person));
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
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

