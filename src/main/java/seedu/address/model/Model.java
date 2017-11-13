package seedu.address.model;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPersonsException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.ReadOnlySchedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyGroup> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlySchedule> PREDICATE_SHOW_ALL_SCHEDULES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Deletes the given persons. */
    void deletePersons(ReadOnlyPerson[] targets) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author cjianhui
    /** Sorts address book list */
    void sortPerson(Comparator<ReadOnlyPerson> sortComparator, boolean isReverseOrder) throws NoPersonsException;

    /** Adds the given group */
    void addGroup(ReadOnlyGroup group) throws DuplicateGroupException;

    /** Deletes the given group */
    void deleteGroup(ReadOnlyGroup group) throws GroupNotFoundException;

    void addPersonToGroup(Index targetGroup, ReadOnlyPerson toAdd) throws
            GroupNotFoundException, PersonNotFoundException, DuplicatePersonException;

    /** Deletes given person from given group */
    void deletePersonFromGroup(Index targetGroup, ReadOnlyPerson toRemove) throws
            GroupNotFoundException, PersonNotFoundException, NoPersonsException;

    //@@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author cjianhui
    /**
     * Replaces the given person {@code target} with {@code favouritePerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updateFavouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritePerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author
    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<ReadOnlyGroup> getFilteredGroupList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate);


}
