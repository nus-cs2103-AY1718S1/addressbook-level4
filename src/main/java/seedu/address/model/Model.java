package seedu.address.model;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.UniqueTagList;

/**
 * The API of the Model component.
 */
public interface Model {
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
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_PINNED_PERSONS = p -> UniqueTagList.containsPinTag(p);
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_UNPINNED_PERSONS = p -> !UniqueTagList.containsPinTag(p);


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
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Pins the given person
     */
    void pinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException;

    /**
     * Unpins the given person
     */
    void unpinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException;

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
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    void sort(String sortType) throws DuplicatePersonException;

    Predicate<ReadOnlyPerson> getPredicateForTags(String tag) throws IllegalValueException;
}
