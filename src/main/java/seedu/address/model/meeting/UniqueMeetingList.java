package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
//import seedu.address.logic.commands.Command;
//import seedu.address.logic.commands.CommandResult;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingClashException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;

//@@author nelsonqyj
/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meeting#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalMeetingList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyMeeting> mappedMeetingList =
            EasyBind.map(internalMeetingList, (meeting) -> meeting);


    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(ReadOnlyMeeting toCheck) {
        requireNonNull(toCheck);
        return internalMeetingList.contains(toCheck);
    }

    /**
     * Returns true if the list contains a clashing meeting that has a different name of meeting as the given argument.
     */
    public boolean diffNameOfMeeting(ReadOnlyMeeting toCheck) {
        for (Meeting meeting : internalMeetingList) {
            if (toCheck.getDate().equals(meeting.getDate())) {
                if (!toCheck.getName().equals(meeting.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns true if the list contains a clashing meeting that has a different name of meeting as the given argument.
     */
    public boolean diffNameOfMeeting(ReadOnlyMeeting toCheck, ReadOnlyMeeting target) {
        for (Meeting meeting : internalMeetingList) {
            if (meeting != target) {
                if (toCheck.getDate().equals(meeting.getDate())) {
                    if (!toCheck.getName().equals(meeting.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the list contains a clashing meeting that has a different meeting location as the given argument.
     */
    public boolean diffLocationOfMeeting(ReadOnlyMeeting toCheck) {
        for (Meeting meeting : internalMeetingList) {
            if (toCheck.getDate().equals(meeting.getDate())) {
                if (!toCheck.getPlace().equals(meeting.getPlace())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns true if the list contains a clashing meeting that has a different meeting location as the given argument.
     */
    public boolean diffLocationOfMeeting(ReadOnlyMeeting toCheck, ReadOnlyMeeting target) {
        for (Meeting meeting : internalMeetingList) {
            if (target != meeting) {
                if (toCheck.getDate().equals(meeting.getDate())) {
                    if (!toCheck.getPlace().equals(meeting.getPlace())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //@@author Melvin-leo
    /**
     * Adds a meeting to the list.
     *
     * @throws DuplicateMeetingException if the meeting to add is a duplicate of an existing meeting in the list.
     */
    public void add(ReadOnlyMeeting toAdd) throws DuplicateMeetingException, MeetingClashException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        } else if (diffNameOfMeeting(toAdd)) {
            throw new MeetingClashException();
        } else if (diffLocationOfMeeting(toAdd)) {
            throw new MeetingClashException();
        }
        internalMeetingList.add(new Meeting(toAdd));
        sort(internalMeetingList);

    }

    /**
     *  Sorts the meeting list in chronological order
     * @param list
     * @return MeetingList
     */
    private ObservableList<Meeting> sort(ObservableList<Meeting> list) {
        list.sort((m1, m2) -> m1.getActualDate(m1.getDate().toString())
                .compareTo(m2.getActualDate(m2.getDate().toString())));

        return list;
    }
    /**
     * Replaces the meeting {@code target} in the list with {@code editedMeeting}.
     *
     * @throws DuplicateMeetingException if the replacement is equivalent to another existing meeting in the list.
     * @throws MeetingNotFoundException if {@code target} could not be found in the list.
     */
    public void setMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedMeeting)
            throws DuplicateMeetingException, MeetingNotFoundException, MeetingClashException {
        requireNonNull(editedMeeting);

        int index = internalMeetingList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }
        if (!target.equals(editedMeeting) && internalMeetingList.contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        } else if (diffNameOfMeeting(editedMeeting, target) || diffLocationOfMeeting(editedMeeting, target)) {
            throw new MeetingClashException();
        }

        internalMeetingList.set(index, new Meeting(editedMeeting));
        sort(internalMeetingList);
    }


    /**
     * To remove pre-edited person and add in the newly edited person
     * @param target
     * @param toAdd
     */
    /*
    public void editPerson(ReadOnlyPerson target, ReadOnlyPerson toAdd) {
        requireNonNull(toAdd);
        internalMeetingList.forEach(meeting -> {
            if (meeting.getPersonsMeet().contains(target)) {
                meeting.getPersonsMeet().remove(target);
                meeting.getPersonsMeet().add(toAdd);
            }
        });
    }
    */
    //@@author nelsonqyj
    /**
     * Removes the equivalent meeting from the list.
     *
     * @throws MeetingNotFoundException if no such meeting could be found in the list.
     */
    public boolean remove(ReadOnlyMeeting toRemove) throws MeetingNotFoundException {
        requireNonNull(toRemove);
        final boolean meetingFoundAndDeleted = internalMeetingList.remove(toRemove);
        if (!meetingFoundAndDeleted) {
            throw new MeetingNotFoundException();
        }
        return meetingFoundAndDeleted;
    }

    public void setMeetings(UniqueMeetingList replacement) {
        this.internalMeetingList.setAll(replacement.internalMeetingList);
    }

    //@@author Melvin-leo
    public void setMeetings(List<? extends ReadOnlyMeeting> meetings) throws DuplicateMeetingException,
                                MeetingClashException {
        final UniqueMeetingList replacement = new UniqueMeetingList();
        for (final ReadOnlyMeeting meeting : meetings) {
            if (dateIsAfter((meeting.getDate().toString()))) { //to delete meetings that have passed automatically
                replacement.add(new Meeting(meeting));
            }
        }
        setMeetings(replacement);
    }
    /**
     * To check if date is after log in (current) date and time
     * @return true if meet date is after current date and time
     */
    private boolean dateIsAfter (String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime meetDate = LocalDateTime.parse(date, formatter);
        if (meetDate.isAfter((currDate))) {
            return true;
        }
        return false;
    }
    //@@author nelsonqyj
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyMeeting> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedMeetingList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalMeetingList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && this.internalMeetingList.equals(((UniqueMeetingList) other).internalMeetingList));
    }

    @Override
    public int hashCode() {
        return internalMeetingList.hashCode();
    }

}
