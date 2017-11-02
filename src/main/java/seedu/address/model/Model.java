package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

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

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author limcel
    /**
     * Deletes the given {@code tag} associated with any person in the addressbook.
     *
     * @throws PersonNotFoundException if there are no persons with {@code tag} in the addressbook.
     * @throws DuplicatePersonException if there are multiple same {@code tag} on a person.
     */
    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException;
    //@@author

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author limcel
    /**
     * Sorts the list in alphabetical order.
     * @throws NullPointerException if {@code contactList} is null.
     */
    ObservableList<ReadOnlyPerson> sortByPersonName();

    /** Adds the given schedule */
    void addSchedule(Schedule schedule) throws PersonNotFoundException;

    /** Adds the given schedule */
    void removeSchedule(Schedule schedule) throws ScheduleNotFoundException;

    /** Returns an unmodifiable view of the schedules list */
    ObservableList<Schedule> getScheduleList();
    //@@author
}
