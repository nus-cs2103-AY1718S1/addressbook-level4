package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicateGroupException;

/**
 * List of groups that enforces uniqueness and doesn't allow nulls
 */

public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> groups = FXCollections.observableArrayList();

    /**
     * returns true if the list contains the group
     */
    public boolean contains (Group toCheck) {
        requireNonNull(toCheck);
        return groups.contains(toCheck);
    }

    /**
     * Adds the given group to the list
     * Throws exception if the group already exists
     * @param toAdd
     */
    public void add (Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        } else {
            groups.add(toAdd);
        }
    }

    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }

    /**
     * @return an FXCollection representing the GroupList as an unmodifiable list
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(groups);
        return FXCollections.unmodifiableObservableList(groups);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && this.groups.equals(((UniqueGroupList) other).groups));
    }

    public void setGroups(UniqueGroupList replacement) {
        this.groups.setAll(replacement.groups);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (int i = 0; i < groups.size(); i++) {
            replacement.add(new Group(groups.get(i).getGroupName()));
        }
        setGroups(replacement);
    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }

}
