package seedu.address.model;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds the given birthday to a person */
    void addBirthday(Index targetIndex, Birthday toAdd) throws PersonNotFoundException,
            DuplicatePersonException;

    //@@author vivekscl
    /** Deletes given tag from every of the given persons */
    void removeTag(ArrayList<Index> targetIndexes, Tag toRemove) throws PersonNotFoundException,
            DuplicatePersonException;

    //@@author vivekscl
    /** Adds given tag to every of the given persons */
    void addTag(ArrayList<Index> targetIndexes, Tag toAdd) throws PersonNotFoundException,
            DuplicatePersonException;

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

    //@@author vivekscl
    /**
     * Uses the JaroWinklerDistance function from the Apache Commons library to find the closest matching name when
     * given keywords that are not found in the FilteredPersonList.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public String getClosestMatchingName(NameContainsKeywordsPredicate predicate);

    /**
     * Sort the given list according to alphabetical order
     * @throws NullPointerException if {@code contactList} is null.
     */
    Boolean sortPersonByName(ArrayList<ReadOnlyPerson> contactList);

}
