package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Favorites the given person. */
    void favoritePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;


    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Sort current person list based on an attribute input by the user
     */
    void sortBy(int attribute);

    /**
     * Creates a group in the addressbook
     * @param groupName of the group to be created
     * @param personToGroup list of person to be included in the newly created group
     */
    void createGroup(String groupName, List<ReadOnlyPerson> personToGroup) throws DuplicateGroupException;

    /**
     * Propagates the edit to group list
     */
    void propagateToGroup(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass);

    /**
     * Deletes the group from group list
     * @param grpToDelete
     */
    void deleteGroup(Group grpToDelete);

    void setGrpName(Group targetGrp, String detail) throws DuplicateGroupException;

    void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws DuplicatePersonException;

    void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws PersonNotFoundException;

    ObservableList<Group> getFilteredGroupList();

    void updateFilteredGroupList(Predicate<Group> predicateShowAllGroups);

    /**
     * Finds the index of a group in the group list
     * @param groupName
     * @return
     */
    Index getGroupIndex(String groupName);
}
