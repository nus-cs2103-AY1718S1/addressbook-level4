package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;

/**
 * Represents a Schedule in an address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule implements ReadOnlySchedule {

    private ObjectProperty<ScheduleName> scheduleName;
    /**
     *  A Schedule will have an empty schedules list by default
     */
    private final UniqueScheduleList schedules = new UniqueScheduleList();

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleName name) {
        requireNonNull(name);
        this.scheduleName = new SimpleObjectProperty<>(name);
    }

    /**
     * Every field must be present and not null.
     */
    public Schedule(String name) throws IllegalValueException {
        requireNonNull(name);
        this.scheduleName = new SimpleObjectProperty<>(new ScheduleName(name));
    }
    /**
     * Creates a copy of the given ReadOnlySchedule.
     */
    public Schedule(ReadOnlySchedule source) {
        this(source.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.scheduleName.toString().equals(((Schedule) other).scheduleName.toString())); // state check
    }

    @Override
    public int hashCode() {
        return scheduleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return getAsText();
    }

    public void addMember(ReadOnlySchedule schedule) throws DuplicateScheduleException {
        this.schedules.add(schedule);
    }

    public void deleteMember(ReadOnlySchedule schedule) throws ScheduleNotFoundException {
        this.schedules.remove(schedule);
    }

    @Override
    public ObjectProperty<ScheduleName> nameProperty() {
        return scheduleName;
    }

    @Override
    public ScheduleName getName() {
        return scheduleName.get();
    }

    public void setScheduleName(ScheduleName name) {
        this.scheduleName.set(requireNonNull(name));
    }

    @Override
    public ObservableList<ReadOnlySchedule> getSchedules() {
        return schedules.asObservableList();
    }

}
