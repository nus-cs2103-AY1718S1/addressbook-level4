package seedu.address.model;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author royceljh
    /** Adds the given event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /** Deletes the given event. */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;

    //@@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author sidhmads
    void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
            throws PersonNotFoundException, IllegalValueException;
    //@@author

    //@@author sidhmads
    void findLocation(List<ReadOnlyPerson> person) throws PersonNotFoundException;
    //@@author

    //@@author soonwj
    /**
     * Sorts all the persons in the address book from most selected to least selected.
     */
    void sortByViewCount();
    //@@author

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    //@@author royceljh
    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    //@@author
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author royceljh
    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);
    //@@author

    //@@author sidhmads
    /**
     * * Updates the filter of the filtered emailTo list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate);

    void sortPersons();
    //@@author
}
