package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;
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
    void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyEventList newEventList);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the EventList */
    ReadOnlyEventList getEventList();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, DeleteOnCascadeException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Adds a person to the specific position in list.
     * Only used to undo deletion
     */
    void addPerson(int position, ReadOnlyPerson person);

    // @@author HouDenghao
    /** Sorts the person list */
    void sortPersons();

    // @@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    Set<Tag> extractNewTag(ReadOnlyPerson person);

    void removeTags(Set<Tag> tagList);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /** Deletes the given event. */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException, DeleteOnCascadeException;

    /** Adds the given event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /**
     * Adds a event to the specific position in list.
     * Only used to undo deletion
     */
    void addEvent(int position, ReadOnlyEvent event);

    // @@author HouDenghao
    /** Sorts the event list */
    void sortEvents();

    // @@author
    /** A participant quit a specific event */
    void quitEvent(Person person, Event event) throws PersonNotParticipateException, NotParticipateEventException;

    void joinEvent(Person person, Event event) throws PersonHaveParticipateException, HaveParticipateEventException;
    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the person's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);
}
