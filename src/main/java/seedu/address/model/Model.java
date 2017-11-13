package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

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

    //@@author justintkj
    /**Sorts all the people in the current database*/
    String sortPerson(String sortType);
    //@@author

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

    void updateListToShowAll();

    //@@author liliwei25
    /**
     * Deletes the given tag from all persons in addressbook
     */
    void removeTag(Tag target) throws UniqueTagList.TagNotFoundException;

    /**
     * Shows the map for selected person in browser
     */
    void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Edits the profile picture for selected person
     */
    void changeImage(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Removes the profile picture for selected person
     */
    void removeImage(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Clears the info panel
     */
    void clearInfoPanel();
}
