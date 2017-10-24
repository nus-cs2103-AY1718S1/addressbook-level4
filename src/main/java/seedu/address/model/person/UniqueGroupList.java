package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

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

    @Override
    public int hashCode() {
        return groups.hashCode();
    }

}
