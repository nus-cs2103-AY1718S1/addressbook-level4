package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.ReadOnlyPerson;

//@@author alexanderleegs
/**
 * A list of meetings that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty MeetingList.
     */
    public UniqueMeetingList() {}

    /**
     * Creates a UniqueMeetingList using given meetings.
     * Enforces no nulls.
     */
    public UniqueMeetingList(Set<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.addAll(meetings);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Meetings in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Meeting> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Meetings in this list with those in the argument Meeting list.
     */
    public void setMeetings(Set<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.setAll(meetings);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Meeting in the argument list exists in this object.
     */
    public void mergeFrom(UniqueMeetingList from) {
        final Set<Meeting> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(meeting -> !alreadyInside.contains(meeting))
                .forEach(internalList::add);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Meeting to the list.
     *
     * @throws DuplicateMeetingException if the Meeting to add is a duplicate of an existing Meeting in the list.
     */
    public void add(Meeting toAdd) throws DuplicateMeetingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author alexanderleegs
    /**
     * Removes the equivalent meeting from the list.
     */
    public boolean remove(Meeting toRemove) {
        requireNonNull(toRemove);
        final boolean meetingFoundAndDeleted = internalList.remove(toRemove);
        return meetingFoundAndDeleted;
    }

    //@@author LimYangSheng
    /**
     * Sorts the meeting list by date.
     */
    public void sortMeeting() {
        Collections.sort(internalList, new Comparator<Meeting>() {
            public int compare(Meeting one, Meeting other) {
                for (int i = 0; i < one.value.length(); i++) {
                    if (one.value.charAt(i) != (other.value.charAt(i))) {
                        if (one.value.charAt(i) > other.value.charAt(i)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    /**
     * Finds the meetings in meeting list with {@code Person} that equals {@code target} and replaces it with
     * {@code editedReadOnlyPerson}
     */
    public void updateMeetings(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson) {
        for (int i = 0; i < internalList.size(); i++) {
            Meeting meeting = new Meeting(internalList.get(i));
            if (meeting.getPerson().equals(target)) {
                meeting.setPerson(editedReadOnlyPerson);
                internalList.set(i, meeting);
            }
        }
    }

    //@@author alexanderleegs
    @Override
    public Iterator<Meeting> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && this.internalList.equals(((UniqueMeetingList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueMeetingList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateMeetingException extends DuplicateDataException {
        protected DuplicateMeetingException() {
            super("Operation would result in duplicate meetings");
        }
    }

}
