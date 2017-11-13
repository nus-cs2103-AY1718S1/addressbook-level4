package seedu.address.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecyclebin);

    /** Clear existing backing Recyclebin and replace with the provided new data*/
    void resetRecyclebin(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();
    ReadOnlyAddressBook getRecycleBin();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException, DuplicatePersonException;
    void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Restore the given person*/
    void restorePerson(ReadOnlyPerson perosn) throws DuplicatePersonException, PersonNotFoundException;
    void restorePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException, DuplicatePersonException;

    /** Sorts the list of persons */
    void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException;

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

    /** Returns an unmodifiable view of the filtered recyclebin list*/
    ObservableList<ReadOnlyPerson> getRecycleBinPersonList();

    /** Returns an unmodifiable view of the upcoming event list */
    ObservableList<Event> getEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);
    void updateFilteredBinList (Predicate<ReadOnlyPerson> predicate);

    /**
     * Replaces the given list of persons {@code target} with the list of edited persons{@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Adds an event to master UniqueEventList.
     * Replace the list of targets with editedPersons to update the UniqueEventList and UniquePersonList.
     *
     * @param targets
     * @param editedPersons
     * @param event
     * @throws DuplicateEventException
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
        throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException;

    /**
     * Remove event(s) from master UniqueEventList.
     * Replace the list of targets with editedPersons to update the UniqueEventList and UniquePersonList.
     *
     * @param targets
     * @param editedPersons
     * @param toRemoveEvents
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     * @throws EventNotFoundException
     */
    void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                      ArrayList<Event> toRemoveEvents)
            throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException;

    /**
     * Sort list of Events based on the the given date.
     *
     * Comparator logic and sorting details is found in {@code UniquePersonList#sort}
     * @param date
     */
    void sortEvents(LocalDate date);

    /**
     * Checks if there exist a clash between list of events and the given event.
     *
     * @param event
     * @return true if a clash exist, otherwise return false
     */
    boolean hasEvenClashes(Event event);
}
