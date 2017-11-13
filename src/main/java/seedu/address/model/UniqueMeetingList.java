package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.exceptions.DuplicateMeetingException;
//@@author liuhang0213
/**
 * A list of meetings that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList implements Iterable<ReadOnlyMeeting>, ReadOnlyMeetingList {

    private final ObservableList<ReadOnlyMeeting> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty MeetingList.
     */
    public UniqueMeetingList() {}

    /**
     * Creates a UniqueMeetingList using given meetings.
     * Enforces no nulls.
     */
    public UniqueMeetingList(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.addAll(meetings);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a copy of an existing meeting list
     */
    public UniqueMeetingList(ReadOnlyMeetingList newData) {
        requireNonNull(newData);
        this.internalList.setAll(newData.getMeetingList());
    }

    @Override
    public ObservableList<ReadOnlyMeeting> getMeetingList() {
        return internalList;
    }

    /**
     * Returns all meetings in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<ReadOnlyMeeting> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Meetings in this list with those in the argument meeting list.
     * @param meetings
     */
    public void setMeetings(ObservableList<ReadOnlyMeeting> meetings) {
        requireAllNonNull(meetings);
        internalList.setAll(meetings);
        assert CollectionUtil.elementsAreUnique(internalList);
    }


    /**
     * Resets the existing data of this {@code UniqueMeetingList} with {@code newData}.
     */
    public void resetData(ReadOnlyMeetingList newData) {
        requireNonNull(newData);
        setMeetings(newData.getMeetingList());
    }

    /**
     * Ensures every meeting in the argument list exists in this object.
     */
    public void mergeFrom(UniqueMeetingList from) {
        final Set<ReadOnlyMeeting> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(meeting -> !alreadyInside.contains(meeting))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Meeting as the given argument.
     */
    public boolean contains(ReadOnlyMeeting toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Meeting to the list.
     *
     * @throws DuplicateMeetingException if the Meeting to add is a duplicate of an existing Meeting in the list.
     */
    public void add(ReadOnlyMeeting toAdd) throws DuplicateMeetingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author Sri-vatsa
    /**
     * Sorts the meeting by date. For retrieving earliest meeting in the list
     */
    public void sortByDate() {
        internalList.sort(new DateTimeComparator());
    }

    /**
     * Comparator that compares date time to sort meetings
     */
    public class DateTimeComparator implements Comparator<ReadOnlyMeeting> {
        /**
         * Custom comparator to compare meetings based on chronological order
         * @param rom1 ReadOnlyMeeting 1
         * @param rom2 ReadOnlyMeeting 2
         * @return which meeting comes before the other
         */
        @Override
        public int compare(ReadOnlyMeeting rom1, ReadOnlyMeeting rom2) {
            return rom1.getDateTimeStr().compareTo(rom2.getDateTimeStr());
        }
    }

    //@@author liuhang0213
    /**
     * Returns the meeting with earliest date in the internal list
     * Currently not checking if it is happening in the future
     */
    @Override
    public ReadOnlyMeeting getUpcomingMeeting() {
        this.sortByDate();
        return internalList.get(0);
    }

    public ObservableList<ReadOnlyMeeting> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<ReadOnlyMeeting> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyMeeting> asObservableList() {
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


}
