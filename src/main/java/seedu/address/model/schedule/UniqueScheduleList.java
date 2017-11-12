package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;

//@@author CT15
/**
 * A list of schedules that enforces no nulls and uniqueness between its elements.
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see Schedule#equals(Object)
 */

public class UniqueScheduleList implements Iterable<Schedule> {

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ScheduleList.
     */
    public UniqueScheduleList() {
    }

    /**
     * Creates a UniqueScheduleList using given schedules.
     * Enforces no nulls.
     */
    public UniqueScheduleList(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.addAll(schedules);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all schedules in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Schedule> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Schedules in this list with those in the argument schedule list.
     */
    public void setSchedules(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.setAll(schedules);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author 17navasaw
    /**
     * Ensures every schedule in the argument list exists in this object.
     */
    public void mergeFrom(UniqueScheduleList from) {
        final Set<Schedule> alreadyInside = this.toSet();

        for (Schedule scheduleFrom : from.internalList) {
            boolean doesScheduleAlreadyExist = false;
            for (Schedule scheduleInside : alreadyInside) {
                if (scheduleFrom.equals(scheduleInside)) {
                    doesScheduleAlreadyExist = true;
                    break;
                }
            }
            if (!doesScheduleAlreadyExist) {
                this.internalList.add(scheduleFrom);
            }
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author
    /**
     * Returns true if the list contains an equivalent Schedule as the given argument.
     */
    public boolean contains(Schedule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Schedule to the list.
     *
     * @throws DuplicateScheduleException if the Schedule to add is a duplicate of an existing Schedule in the list.
     */
    public void add(Schedule toAdd) throws DuplicateScheduleException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateScheduleException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Schedule> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Schedule> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@@author 17navasaw
    /**
     * Sorts the list from earliest to latest schedule.
     */
    public void sort() {
        FXCollections.sort(internalList, (schedule1, schedule2) -> {
            String schedule1DateInString = schedule1.getScheduleDate().value;
            String schedule2DateInString = schedule2.getScheduleDate().value;

            int schedule1Year = DateUtil.getYear(schedule1DateInString);
            int schedule2Year = DateUtil.getYear(schedule2DateInString);
            if (schedule1Year != schedule2Year) {
                return schedule1Year - schedule2Year;
            }

            int schedule1Month = DateUtil.getMonth(schedule1DateInString);
            int schedule2Month = DateUtil.getMonth(schedule2DateInString);
            if (schedule1Month != schedule2Month) {
                return schedule1Month - schedule2Month;
            }

            int schedule1Day = DateUtil.getDay(schedule1DateInString);
            int schedule2Day = DateUtil.getDay(schedule2DateInString);
            return schedule1Day - schedule2Day;
        });
    }

    //@@author CT15
    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduleList // instanceof handles nulls
                && this.internalList.equals(((UniqueScheduleList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueScheduleList other) {
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
    public static class DuplicateScheduleException extends DuplicateDataException {
        protected DuplicateScheduleException() {
            super("Operation would result in duplicate schedules");
        }
    }

}

