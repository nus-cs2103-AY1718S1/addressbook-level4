package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;

//@@author limcel
/**
 * A list of schedules that enforces no nulls between its elements.
 *
 * @see Schedule#equals(Object)
 */
public class UniqueScheduleList implements Iterable<Schedule> {

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ScheduleList.
     */
    public UniqueScheduleList() {}

    /**
     * Creates a ScheduleList using given selected persons.
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
     * Replaces the schedules in this list with those in the argument schedules list.
     */
    public void setSchedule(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.setAll(schedules);
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
     * Adds a schedule to the list.
     */
    public void add(Schedule toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Schedule from the list.
     *
     * @throws ScheduleNotFoundException if the Schedule to remove cannot be found as an existing Schedule in the list.
     */
    public void remove(Schedule toRemove) throws ScheduleNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new ScheduleNotFoundException();
        }
        internalList.remove(toRemove);

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

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
