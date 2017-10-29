package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.module.exceptions.DuplicateRemarkException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

/**
 * A list of remarks that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Remark#equals(Object)
 */
public class UniqueRemarkList implements Iterable<Remark> {

    private final ObservableList<Remark> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty LecturerList.
     */
    public UniqueRemarkList() {}

    /**
     * Creates a UniqueLecturerList using given remarks.
     * Enforces no nulls.
     */
    public UniqueRemarkList(Set<Remark> remarks) {
        requireAllNonNull(remarks);
        internalList.addAll(remarks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all remarks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Remark> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the remarks in this list with those in the argument Remark list.
     */
    public void setRemarks(Set<Remark> remarks) {
        requireAllNonNull(remarks);
        internalList.setAll(remarks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the remark {@code target} in the list with {@code editedRemark}.
     *
     * @throws DuplicateRemarkException if the replacement is equivalent to another existing lesson in the list.
     * @throws RemarkNotFoundException if {@code target} could not be found in the list.
     */
    public void setRemark(Remark target, Remark editedRemark)
            throws DuplicateRemarkException, RemarkNotFoundException {
        requireNonNull(editedRemark);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RemarkNotFoundException();
        }

        if (!target.equals(editedRemark) && internalList.contains(editedRemark)) {
            throw new DuplicateRemarkException();
        }

        internalList.set(index, new Remark(editedRemark));
    }


    /**
     * Returns true if the list contains an equivalent remark as the given argument.
     */
    public boolean contains(Remark toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Remark to the list.
     *
     * @throws DuplicateRemarkException if the Lecturer to add is a duplicate of an existing Remark in the list.
     */
    public void add(Remark toAdd) throws DuplicateRemarkException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRemarkException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent remark from the list.
     *
     * @throws RemarkNotFoundException if no such remark could be found in the list.
     */
    public boolean remove(Remark toRemove) throws RemarkNotFoundException {
        requireNonNull(toRemove);
        final boolean remarkFoundAndDeleted = internalList.remove(toRemove);
        if (!remarkFoundAndDeleted) {
            throw new RemarkNotFoundException();
        }
        return remarkFoundAndDeleted;
    }

    @Override
    public Iterator<Remark> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Remark> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueRemarkList // instanceof handles nulls
                && this.internalList.equals(((UniqueRemarkList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRemarkList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }


}
