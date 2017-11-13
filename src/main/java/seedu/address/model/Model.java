package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
import seedu.address.model.meeting.exceptions.MeetingClashException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
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

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyMeeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds the given meeting */
    void addMeeting(ReadOnlyMeeting meeting)  throws DuplicateMeetingException, MeetingBeforeCurrDateException,
            MeetingClashException;

    /** Deletes the given meeting */
    void deleteMeeting(ReadOnlyMeeting target) throws MeetingNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Replaces the given meeting {@code target} with {@code editedMeeting}.
     *
     * @throws DuplicateMeetingException if updating the meeting's details causes the meeting to be equivalent to
     *      another existing meeting in the list.
     * @throws MeetingNotFoundException if {@code target} could not be found in the list.
     * @throws MeetingBeforeCurrDateException if udating the meeting's details cause the meeting to be before the
     *      current system date.
     * @throws MeetingClashException if updating the meeting's datetime causes the meeting to clash with another
     *      meeting's datetime.
     */
    void updateMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedMeeting)
            throws DuplicateMeetingException, MeetingNotFoundException, MeetingBeforeCurrDateException,
            MeetingClashException;

    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyMeeting> getFilteredMeetingList();

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<ReadOnlyMeeting> predicate);

}
