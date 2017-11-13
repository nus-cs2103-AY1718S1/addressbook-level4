package seedu.address.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    //@@author eldonng
    /**
     * Comparators for sorting purposes
     */
    Comparator<ReadOnlyPerson> COMPARATOR_SORT_BY_NAME = (
        ReadOnlyPerson p1, ReadOnlyPerson p2) -> p1.getName().compareTo(p2.getName());
    Comparator<ReadOnlyPerson> COMPARATOR_SORT_BY_PHONE = (
        ReadOnlyPerson p1, ReadOnlyPerson p2) -> p1.getPhone().compareTo(p2.getPhone());
    Comparator<ReadOnlyPerson> COMPARATOR_SORT_BY_EMAIL = (
        ReadOnlyPerson p1, ReadOnlyPerson p2) -> p1.getEmail().compareTo(p2.getEmail());
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_PINNED_PERSONS = p -> p.isPinned();
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_UNPINNED_PERSONS = p -> !p.isPinned();
    Predicate<ReadOnlyGroup> PREDICATE_SHOW_ALL_GROUPS = unused -> true;


    //@@author
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Deletes the given person.
     */
    void deletePerson(ReadOnlyPerson... target) throws PersonNotFoundException;

    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author eldonng
    /**
     * Adds the given group
     */
    void addGroup(ReadOnlyGroup group) throws DuplicateGroupException;

    /**
     * Deletes the given group
     */
    void deleteGroup(ReadOnlyGroup group) throws GroupNotFoundException;

    /**
     * Pins the given person
     */
    void pinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException, EmptyAddressBookException;

    /**
     * Unpins the given person
     */
    void unpinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException, EmptyAddressBookException;

    /**
     * Set the colour for the specific tag
     * @param tag
     * @param colour
     */
    void setTagColour(String tag, String colour) throws IllegalValueException;

    HashMap<Tag, String> getTagColours();

    //@@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered group list
     */
    ObservableList<ReadOnlyGroup> getGroupList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate);

    void sort(String sortType) throws DuplicatePersonException, EmptyAddressBookException;

    Predicate<ReadOnlyPerson> getPredicateForTags(String tag) throws IllegalValueException;
}
