package seedu.address.model;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author chernghann
    /** Adds the given event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;
    //@@author

    //@@author itsdickson
    /** Deletes the given event */
    void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException;

    /** Favourites the given person */
    void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Unfavourites the given person */
    void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author

    void updateSelectedPerson(ReadOnlyPerson person);

    void unselectPerson();

    boolean ifSelectedPerson();

    ReadOnlyPerson getSelectedPerson();

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

    //@@author chernghann
    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();
    //@@author

    //@@author itsdickson
    /** Returns the themes list */
    ArrayList<String> getThemesList();

    /** Sets the current theme */
    void setCurrentTheme(String theme);

    /** Returns the current theme */
    String getCurrentTheme();
    //@@author

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author chernghann
    /**
     * Updates the filter of the filtered events list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);
    //@@author

    /** Sorts the address book person list */
    void sort();
}
