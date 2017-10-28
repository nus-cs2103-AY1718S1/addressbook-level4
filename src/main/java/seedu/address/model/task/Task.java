package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Appointment> name;
    private ObjectProperty<Date> date;
    private ObjectProperty<StartTime> startTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Appointment name, Date date, StartTime startTime) {
        requireAllNonNull(name, date, startTime);
        this.name = new SimpleObjectProperty<>(name);
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
        return name;
    }

    @Override
    public Appointment getAppointment() {
        return name.get();
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    @Override
    public ObjectProperty<StartTime> startTimeProperty() {
        return startTime;
    }

    @Override
    public StartTime getStartTime() {
        return startTime.get();
    }
}
