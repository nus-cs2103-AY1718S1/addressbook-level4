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

    /**
     * Ensures every schedule in the argument list exists in this object.
     */
    public void mergeFrom(UniqueScheduleList from) {
        final Set<Schedule> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(schedule -> !alreadyInside.contains(schedule))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

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

