package seedu.address.model;

import java.text.ParseException;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.group.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.function.Predicate;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    void addGroup(Group group) throws DuplicateGroupException,IllegalValueException;

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

    //@@author erik0704
    /** Adds the given event */
    void addEvent(Event event) throws DuplicateEventException;

    /** Deletes the given person. */
    void deleteEvent(Event target) throws EventNotFoundException;

    /** Returns a view of the filtered event list */
    ObservableList<Event> getFilteredEventList();
    /** Return a view of upcoming (in 1 day) event list */
    ObservableList<Event> getUpcomingEventList() throws ParseException;

    void deleteGroup(Group target) throws GroupNotFoundException;

    /** Returns a view of the filtered group list */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);
    //@@author

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

}
