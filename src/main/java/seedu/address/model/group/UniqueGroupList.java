package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author eldonng
/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyGroup> mappedList = EasyBind.map(internalList, (group) -> group);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    public boolean contains(ReadOnlyGroup toCheck) {
        requireNonNull(toCheck);
        for (Group group: internalList) {
            if (group.getGroupName().equals(toCheck.getGroupName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a group to the list.
     *
     * @throws DuplicateGroupException if the group to add is a duplicate of an existing group in the list.
     */
    public void add(ReadOnlyGroup toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(new Group(toAdd));
    }

    /**
     * Removes the equivalent group from the list.
     *
     * @throws GroupNotFoundException if no such group could be found in the list.
     */
    public boolean remove(ReadOnlyGroup toRemove) throws GroupNotFoundException {
        requireNonNull(toRemove);
        final boolean groupFoundAndDeleted = internalList.remove(toRemove);
        if (!groupFoundAndDeleted) {
            throw new GroupNotFoundException();
        }
        return groupFoundAndDeleted;
    }

    /**
     * Removes the equivalent person from all groups
     * @param toRemove
     */
    public void removePerson(ReadOnlyPerson toRemove) {
        requireNonNull(toRemove);
        internalList.forEach(group -> {
            if (group.getGroupMembers().contains(toRemove)) {
                group.getGroupMembers().remove(toRemove);
            }
        });
    }

    /**
     * replaces the target person with the new information to all groups
     * @param target
     * @param toAdd
     */
    public void editPerson(ReadOnlyPerson target, ReadOnlyPerson toAdd) {
        requireNonNull(toAdd);
        internalList.forEach(group -> {
            if (group.getGroupMembers().contains(target)) {
                group.getGroupMembers().remove(target);
                group.getGroupMembers().add(toAdd);
            }
        });
    }

    public void setGroups(UniqueGroupList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setGroups(List<? extends ReadOnlyGroup> groups) throws DuplicateGroupException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final ReadOnlyGroup group : groups) {
            replacement.add(new Group(group));
        }
        setGroups(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyGroup> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && this.internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
