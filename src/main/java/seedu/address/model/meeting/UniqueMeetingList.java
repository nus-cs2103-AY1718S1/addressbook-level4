package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;

/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meeting#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyMeeting> mappedList = EasyBind.map(internalList, (meeting) -> meeting);

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(ReadOnlyMeeting toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a meeting to the list.
     *
     * @throws DuplicateMeetingException if the meeting to add is a duplicate of an existing meeting in the list.
     */
    public void add(ReadOnlyMeeting toAdd) throws DuplicateMeetingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(new Meeting(toAdd));
        internalList.sort((m1,m2)-> m1.getActualDate(m1.getDate().toString())
                .compareTo(m2.getActualDate(m2.getDate().toString())));
    }

    /**
     * Replaces the meeting {@code target} in the list with {@code editedMeeting}.
     *
     * @throws DuplicateMeetingException if the replacement is equivalent to another existing meeting in the list.
     * @throws MeetingNotFoundException if {@code target} could not be found in the list.
     */
    public void setMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedMeeting)
            throws DuplicateMeetingException, MeetingNotFoundException {
        requireNonNull(editedMeeting);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }

        if (!target.equals(editedMeeting) && internalList.contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        }

        internalList.set(index, new Meeting(editedMeeting));
    }

    /**
     * Removes the equivalent meeting from the list.
     *
     * @throws MeetingNotFoundException if no such meeting could be found in the list.
     */
    public boolean remove(ReadOnlyMeeting toRemove) throws MeetingNotFoundException {
        requireNonNull(toRemove);
        final boolean meetingFoundAndDeleted = internalList.remove(toRemove);
        if (!meetingFoundAndDeleted) {
            throw new MeetingNotFoundException();
        }
        return meetingFoundAndDeleted;
    }

    public void setMeetings(UniqueMeetingList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setMeetings(List<? extends ReadOnlyMeeting> meetings) throws DuplicateMeetingException {
        final UniqueMeetingList replacement = new UniqueMeetingList();
        for (final ReadOnlyMeeting meeting : meetings) {
            replacement.add(new Meeting(meeting));
        }
        setMeetings(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyMeeting> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && this.internalList.equals(((UniqueMeetingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
