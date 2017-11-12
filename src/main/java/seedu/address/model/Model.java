package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_FAVOURITE_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author siri99
    /** Deletes the given person from Favourite List. */
    void removeFavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author siri99

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author siri99
    /** Adds the given person into Favourite List */
    void addFavouritePerson(ReadOnlyPerson person) throws DuplicatePersonException;
    //@@author siri99

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author siri99
    String getCurrentList();

    void setCurrentList(String currentList);
    //@@author siri99

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    //@@author siri99
    /** Returns an unmodifiable view of the filtered favourite person list */
    ObservableList<ReadOnlyPerson> getFilteredFavouritePersonList();

    /** Sorts the list in alphabhetical order of names */
    void sortFilteredPersonListName();

    /** Sorts the list in ascending order of birthdays : Jan to Dec */
    void sortFilteredPersonListBirthday();
    //@@author siri99

    //@@author Henning
    /** Sorts the list by groups score, in descending order*/
    void sortFilteredPersonListScore();
    //@@author Henning

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author siri99
    void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate);

    void changeListTo(String listName);
    //@@author siri99
}
