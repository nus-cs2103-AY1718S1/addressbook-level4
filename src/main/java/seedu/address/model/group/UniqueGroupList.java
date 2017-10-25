package seedu.address.model.group;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * A list of groups that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Group#equals(Object)
 */


public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyGroup> mappedList = EasyBind.map(internalList, (group) -> group);

    /**
     * Creates a UniqueGroupList using given Groups.
     * Enforces no nulls.
     */
    public UniqueGroupList(Set<Group> groups) {
        requireAllNonNull(groups);
        internalList.addAll(groups);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Constructs empty UniqueGroupList.
     */
    public UniqueGroupList() {}

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(ReadOnlyGroup toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns all groups in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Group> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Ensures every group in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final Set<Group> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(group -> !alreadyInside.contains(group))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Adds a Group to the list.
     *
     * @throws seedu.address.model.group.exceptions.DuplicateGroupException
     * if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(ReadOnlyGroup toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(new Group(toAdd));

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent person from the list.
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
     * Adds person to specified group in the list.
     *
     * @throws GroupNotFoundException if no such group could be found in the list.
     */
    public void addPersonToGroup(Index target, ReadOnlyPerson toAdd)
            throws GroupNotFoundException, DuplicatePersonException, PersonNotFoundException {
        requireNonNull(toAdd);
        requireNonNull(target);

        Group targetGroup = internalList.get(target.getZeroBased());

        if (isNull(targetGroup)) {
            throw new GroupNotFoundException();
        }

        try {
            targetGroup.addMember(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new DuplicatePersonException();
        }

        internalList.set(target.getZeroBased(), targetGroup);
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
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
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.group.UniqueGroupList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.group.UniqueGroupList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(seedu.address.model.group.UniqueGroupList other) {
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

