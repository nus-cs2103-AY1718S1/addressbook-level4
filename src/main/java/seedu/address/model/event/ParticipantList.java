package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Person;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a Event's participant list in the address book.
 */

public class ParticipantList implements Iterable<Person> {
    private ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * construct empty participant list
     */
    public ParticipantList() {}

    /**
     * Construct participant list using given participants
     * Enforces no nulss
     */
    public ParticipantList(Set<Person> participants) {
        requireNonNull(participants);
        internalList.addAll(participants);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all participants in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */

    public Set<Person> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Participants in this list with those in the argument Participant list.
     */
    public void setTags(Set<Person> participants) {
        requireAllNonNull(participants);
        internalList.setAll(participants);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every participant in the argument list exists in this object.
     */
    public void mergeFrom(ParticipantList from) {
        final Set<Person> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(participant -> !alreadyInside.contains(participant))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Participant as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Participant to the list.
     *
     * @throws DuplicatePersonException if the Tag to add is a duplicate of an existing Participant in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove a Participant in the list.
     *
     * @throws seedu.address.model.person.exceptions.PersonNotFoundException if the Tag to remove is not in the list.
     */
    public void remove(Person toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new TagNotFoundException();
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof ParticipantList // instanceof handles nulls
                && this.internalList.equals(((ParticipantList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(ParticipantList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

    /**
     * Signals that a certain Participant is not found in the list.
     */
    public static class TagNotFoundException extends IllegalArgumentException {
        protected TagNotFoundException() {
            super("This person does not exist in o participantList");
        }
    }

}
