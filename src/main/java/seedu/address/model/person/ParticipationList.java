package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
// @@author HuWanqing
/**
 * Represents all events which a person participates.
 */
public class ParticipationList implements Iterable<Event> {

    private ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * construct empty participation list
     */
    public ParticipationList() {}

    /**
     * Construct participation list using given participation
     * Enforces no nulls
     */
    public ParticipationList(Set<Event> participation) {
        requireNonNull(participation);
        internalList.addAll(participation);

        assert CollectionUtil.elementsAreUnique(internalList);
    }
    /**
     * Returns all participation in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Event> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns true if the participation list contains a specific event given by argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the participation list.
     *
     * @throws DuplicateEventException if this person has already participate this event
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove an participated event from the list.
     *
     * @throws EventNotFoundException if the does not participate this event.
     */
    public void remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new EventNotFoundException();
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }
    // @@author

    @Override
    public Iterator<Event> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof ParticipationList // instanceof handles nulls
                && this.internalList.equals(((ParticipationList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
