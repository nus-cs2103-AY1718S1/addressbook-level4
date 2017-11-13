// @@author tanchc
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Task in ContactHub.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Appointment> appointment;
    private ObjectProperty<Date> date;
    private ObjectProperty<StartTime> startTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Appointment appointment, Date date, StartTime startTime) {
        requireAllNonNull(appointment, date, startTime);
        this.appointment = new SimpleObjectProperty<>(appointment);
        this.date = new SimpleObjectProperty<>(date);
        this.startTime = new SimpleObjectProperty<>(startTime);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getAppointment(), source.getDate(), source.getStartTime());
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public ObjectProperty<Appointment> appointmentProperty() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment.set(requireNonNull(appointment));
    }

    @Override
    public Appointment getAppointment() {
        return appointment.get();
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    @Override
    public ObjectProperty<StartTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime.set(requireNonNull(startTime));
    }

    @Override
    public StartTime getStartTime() {
        return startTime.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
}
