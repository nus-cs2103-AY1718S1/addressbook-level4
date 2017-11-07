package seedu.address.model;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
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

    //@@author john19950730
    /** Returns all names in the AddressBook */
    List<String> getAllNamesInAddressBook();

    /** Returns all phones in the AddressBook */
    List<String> getAllPhonesInAddressBook();

    /** Returns all emails in the AddressBook */
    List<String> getAllEmailsInAddressBook();

    /** Returns all addresses in the AddressBook */
    List<String> getAllAddressesInAddressBook();

    /** Returns all tags in the AddressBook */
    List<String> getAllTagsInAddressBook();

    /** Returns all remarks in the AddressBook */
    List<String> getAllRemarksInAddressBook();

    //@@author
    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

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

    /** Removes a tag from every person in the list.*/
    void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    /** Removes a tag from the specified Index on the list.*/
    void removeTag(Index index, Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    /** Sorts the filtered list.*/
    void sortFilteredPersonList(Comparator<ReadOnlyPerson> comparator);
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

}
