package seedu.address.model.relationship;

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
 * A list of tags that enforces no nulls and uniqueness between its elements.
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see Relationship#equals(Object)
 */
public class UniqueRelList implements Iterable<Relationship> {

    private final ObservableList<Relationship> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueRelList() {
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueRelList(Set<Relationship> rel) {
        requireAllNonNull(rel);
        internalList.addAll(rel);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Relationship> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setRel(Set<Relationship> rel) {
        requireAllNonNull(rel);
        internalList.setAll(rel);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueRelList from) {
        final Set<Relationship> alreadyInside = this.toSet();
        from.internalList.stream()
        .filter(rel -> !alreadyInside.contains(rel))
            .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Relationship toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Relationship to the list.
     *
     * @throws DuplicateRelException if the Relationship to add is a duplicate of an existing Tag in the list.
     */
    public void add(Relationship toAdd) throws DuplicateRelException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRelException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Tag from the list if it is present.
     *
     * @param toRemove Tag to be removed
     */
    public void remove(Relationship toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Relationship> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Relationship> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
            || (other instanceof UniqueRelList // instanceof handles nulls
            && this.internalList.equals(((UniqueRelList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRelList other) {
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
    public static class DuplicateRelException extends DuplicateDataException {
        protected DuplicateRelException() {
            super("Operation would result in duplicate relationship");
        }
    }

}
