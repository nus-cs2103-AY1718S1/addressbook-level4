package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.DuplicateMeetingException;
import seedu.address.model.exceptions.IllegalIdException;
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
    void resetData(ReadOnlyAddressBook newData, ReadOnlyMeetingList newMeetingData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the meeting list */
    ReadOnlyMeetingList getMeetingList();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author Sri-vatsa
    /** Deletes given tag from everyone in the addressbook */
    boolean deleteTag(Tag [] tags) throws PersonNotFoundException, DuplicatePersonException;
    /** Adds the given person */
    void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException;
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

    //@@author martyn-wong
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    default void updateFilteredPersonList() {
        updateFilteredPersonList();
    }
    //@@author

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Returns the userPref which the model is initialized with
     * @return UserPrefs object
     */
    UserPrefs getUserPrefs();

    //@@author Sri-vatsa
    /**
     * Updates search count for each person who is searched using {@code FindCommand}
     * Assumes filtered List of persons contains search results
     */
    void recordSearchHistory() throws CommandException;

    /**
     * Sort everyone in addressbook by searchCount
     */
    void sortPersonListBySearchCount();

    /**
     * Sort everyone in addressbook lexicographically
     */
    void sortPersonListLexicographically();

    //@@author martyn-wong
    /**
     * Shows the google map for the selected person in the browser panel
     */
    void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException;
}
