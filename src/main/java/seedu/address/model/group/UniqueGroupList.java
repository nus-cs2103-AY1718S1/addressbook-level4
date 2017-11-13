//@@author hthjthtrh
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Adds a group to the list.
     */
    public void add(Group grp) throws DuplicateGroupException {
        requireNonNull(grp);
        if (contains(grp)) {
            throw new DuplicateGroupException();
        }
        internalList.add(grp);
        sort();
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        for (Group group : internalList) {
            if (group.getGrpName().equals(toCheck.getGrpName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * sorts the list of groups based on alphabetical order
     */
    public void sort() {
        internalList.sort(Comparator.comparing(Group::getGrpName));
    }


    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group grp : groups) {
            replacement.add(new Group(grp));
        }
        this.internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Removes the specified group from the group list
     *
     * @param grpToDelete
     */
    public void removeGroup(Group grpToDelete) {
        internalList.remove(grpToDelete);
        sort();
    }

    /**
     * sets the group to the new name
     *
     * @param targetGrp group to change name
     * @param newName   new name to change to
     * @throws DuplicateGroupException if a group of newName already exists in the group list
     */
    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        for (Group grp : internalList) {
            if (grp.getGrpName().equals(newName)) {
                throw new DuplicateGroupException();
            }
            if (targetGrp.getGrpName().equals(grp.getGrpName())) {
                targetGrp = grp;
                break;
            }
        }
        targetGrp.setGrpName(newName);
        sort();
    }

    /**
     * get the index of a group in the group list
     * @param groupName
     * @return -1 if the group isn't in the group list
     */
    public int getGroupIndex(String groupName) {
        for (Group grp : internalList) {
            if (grp.getGrpName().equals(groupName)) {
                return internalList.indexOf(grp);
            }
        }
        return -1;
    }

    /**
     * removes a person from the group
     * @param targetGrp
     * @param targetPerson
     */
    public void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        try {
            targetGrp.remove(targetPerson);
            targetGrp.updatePreviews();
        } catch (PersonNotFoundException e) {
            assert false : "This person should be in the group";
        }
    }

    /**
     * adds target person into target group
     * @param targetGrp
     * @param targetPerson
     * @throws DuplicatePersonException
     */
    public void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws DuplicatePersonException {
        targetGrp.add(targetPerson);
        targetGrp.updatePreviews();
    }
}
//@@author
