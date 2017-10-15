package seedu.address.model.lecturer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of Lecturers that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Lecturer#equals(Object)
 */
public class UniqueLecturerList implements Iterable<Lecturer> {

    private final ObservableList<Lecturer> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty LecturerList.
     */
    public UniqueLecturerList() {}

    /**
     * Creates a UniqueLecturerList using given lecturers.
     * Enforces no nulls.
     */
    public UniqueLecturerList(Set<Lecturer> lecturers) {
        requireAllNonNull(lecturers);
        internalList.addAll(lecturers);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all lecturers in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Lecturer> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the lecturers in this list with those in the argument Lecturer list.
     */
    public void setLectuers(Set<Lecturer> lectuers) {
        requireAllNonNull(lectuers);
        internalList.setAll(lectuers);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Lecturer in the argument list exists in this object.
     */
    public void mergeFrom(UniqueLecturerList from) {
        final Set<Lecturer> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(lecturer -> !alreadyInside.contains(lecturer))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Lecturer as the given argument.
     */
    public boolean contains(Lecturer toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Lecturer to the list.
     *
     * @throws DuplicateLecturerException if the Lecturer to add is a duplicate of an existing Lecturer in the list.
     */
    public void add(Lecturer toAdd) throws DuplicateLecturerException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLecturerException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Lecturer> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lecturer> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueLecturerList // instanceof handles nulls
                        && this.internalList.equals(((UniqueLecturerList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueLecturerList other) {
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
    public static class DuplicateLecturerException extends DuplicateDataException {
        protected DuplicateLecturerException() {
            super("Operation would result in duplicate lecturers");
        }
    }

}
