package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;


/**
 * A list of schedules that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Schedule#equals(Object)
 */


public class UniqueScheduleList implements Iterable<Schedule> {

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlySchedule> mappedList = EasyBind.map(internalList, (schedule) -> schedule);

    /**
     * Creates a UniqueScheduleList using given Schedules.
     * Enforces no nulls.
     */
    public UniqueScheduleList(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.addAll(schedules);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Constructs empty ScheduleList.
     */
    public UniqueScheduleList() {}

    /**
     * Returns all schedules in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */

    /**
     * Returns true if the list contains an equivalent Schedule as the given argument.
     */
    public boolean contains(ReadOnlySchedule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns a set representation of the schedule.
     */
    public Set<Schedule> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
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
     * Adds a Schedule to the list.
     *
     * @throws seedu.address.model.schedule.exceptions.DuplicateScheduleException
     * if the Schedule to add is a duplicate of an existing Schedule in the list.
     */
    public void add(ReadOnlySchedule toAdd) throws DuplicateScheduleException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateScheduleException();
        }
        internalList.add(new Schedule(toAdd));

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent schedule from the list.
     *
     * @throws ScheduleNotFoundException if no such schedule could be found in the list.
     */
    public boolean remove(ReadOnlySchedule toRemove) throws ScheduleNotFoundException {
        requireNonNull(toRemove);
        final boolean scheduleFoundAndDeleted = internalList.remove(toRemove);
        if (!scheduleFoundAndDeleted) {
            throw new ScheduleNotFoundException();
        }
        return scheduleFoundAndDeleted;
    }

    @Override
    public Iterator<Schedule> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    public void setSchedules(UniqueScheduleList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setSchedules(List<? extends ReadOnlySchedule> schedules) throws DuplicateScheduleException {
        final UniqueScheduleList replacement = new UniqueScheduleList();
        for (final ReadOnlySchedule schedule: schedules) {
            replacement.add(new Schedule(schedule));
        }
        setSchedules(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlySchedule> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.schedule.UniqueScheduleList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.schedule.UniqueScheduleList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(seedu.address.model.schedule.UniqueScheduleList other) {
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

