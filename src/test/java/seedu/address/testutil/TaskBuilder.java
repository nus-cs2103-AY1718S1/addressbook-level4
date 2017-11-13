// @@author tanchc, ahmadalkaff
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_APPOINTMENT = "Meeting";
    public static final String DEFAULT_DATE = "25/11/2017";
    public static final String DEFAULT_START_TIME = "12:00";

    private Task task;

    public TaskBuilder() {
        try {
            Appointment defaultAppointment = new Appointment(DEFAULT_APPOINTMENT);
            Date defaultDate = new Date(DEFAULT_DATE);
            StartTime defaultStartTime = new StartTime(DEFAULT_START_TIME);
            this.task = new Task(defaultAppointment, defaultDate, defaultStartTime);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Appointment} of the {@code Task} that we are building.
     */

    public TaskBuilder withAppointment(String appointment) {
        try {
            this.task.setAppointment(new Appointment(appointment));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("appointment is expected to be alphanumeric.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        try {
            this.task.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartTime(String startTime) {
        try {
            this.task.setStartTime(new StartTime(startTime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("startTime is expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
