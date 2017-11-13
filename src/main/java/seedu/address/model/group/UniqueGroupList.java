package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

//@@author tingtx
/**
 * A list of groups that enforces no nulls and uniqueness between its elements.
 * Supports minimal set of list operations for the app's features.
 *
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty GroupList.
     */
    public UniqueGroupList() {
    }


    /**
     * Replaces the group in this list with those in the argument group list.
     */
    public void setGroup(Set<Group> groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Gr oup as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Group to the list only if it is not already exist and Group does not contains empty value.
     */
    public void addIfNew(Group toAdd) {
        requireNonNull(toAdd);
        if (!toAdd.value.isEmpty() && !contains(toAdd)) {
            internalList.add(toAdd);
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Deletes a Group from the list.
     */
    public void delete(Group toDelete) {
        requireNonNull(toDelete);
        internalList.remove(toDelete);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueGroupList other) {
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
    public static class DuplicateGroupException extends DuplicateDataException {
        protected DuplicateGroupException() {
            super("Operation would result in duplicate groups");
        }
    }

}
