package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.InvalidSortTypeException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

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
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;
    //@@author huiyiiih
    /**
     * Sorts person list according to user input option
     */
    void sortPerson(String type) throws InvalidSortTypeException;
    //@author
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

    //@@author reginleiff
    /**
     * Adds the given event
     */
    void addEvent(ReadOnlyEvent event) throws EventTimeClashException;

    /**
     *  Deletes the given event.
     */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     * Replaces the given event {@code target} with {@code editedPerson}.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws EventNotFoundException,
            EventTimeClashException;

    /**
     * Returns an unmodifiable view of the filtered event list.
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the timetable.
     */
    ObservableList<ReadOnlyEvent> getTimetable();

    /**
     * @throws NullPointerException if {@code predicate} is null.
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);

    //@@author shuang-yang
    /**
     * Schedule a repeated event.
     */
    void scheduleRepeatedEvent(ReadOnlyEvent event);
}
