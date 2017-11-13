package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Schedule in an address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule implements ReadOnlySchedule {

    private ObjectProperty<ScheduleName> scheduleName;
    private ObjectProperty<ScheduleDate> startDateTime;
    private ObjectProperty<ScheduleDate> endDateTime;
    private SimpleStringProperty scheduleDuration;
    private SimpleStringProperty scheduleDetails = new SimpleStringProperty();

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleName name, ScheduleDate startDateTime, ScheduleDate endDateTime,
                    String scheduleDuration, String scheduleDetails) {
        requireNonNull(name);
        this.scheduleName = new SimpleObjectProperty<>(name);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.scheduleDuration = new SimpleStringProperty(scheduleDuration);
        this.scheduleDetails = new SimpleStringProperty(scheduleDetails);
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
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(),
                source.getScheduleDuration(), source.getScheduleDetails());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.scheduleName.toString().equals(((Schedule) other).scheduleName.toString())
                && this.startDateTime.toString().equals(((Schedule) other).startDateTime.toString())
                && this.endDateTime.toString().equals(((Schedule) other).endDateTime.toString())); // state check
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


    @Override
    public ObjectProperty<ScheduleName> nameProperty() {
        return scheduleName;
    }

    @Override
    public ScheduleName getName() {
        return scheduleName.get();
    }

    @Override
    public ObjectProperty<ScheduleDate> startDateTimeProperty() {
        return startDateTime;
    }

    @Override
    public ScheduleDate getStartDateTime() {
        return startDateTime.get();
    }

    @Override
    public ObjectProperty<ScheduleDate> endDateTimeProperty() {
        return endDateTime;
    }

    @Override
    public ScheduleDate getEndDateTime() {
        return endDateTime.get();
    }

    public void setScheduleName(ScheduleName name) {
        this.scheduleName.set(requireNonNull(name));
    }

    @Override
    public String getScheduleDuration() {
        return scheduleDuration.get();
    }

    @Override
    public SimpleStringProperty scheduleDurationProperty() {
        return scheduleDuration;
    }

    @Override
    public String getScheduleDetails() {
        return scheduleDetails.get();
    }

    @Override
    public SimpleStringProperty scheduleDetailsProperty() {
        return scheduleDetails;
    }

}
