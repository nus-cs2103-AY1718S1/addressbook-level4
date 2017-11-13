package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
//@@author RonakLakhotia
/**
 * Represents a Reminder in the Weaver.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Reminder implements ReadOnlyReminder {

    private ObjectProperty<ReminderDetails> details;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<DueDate> dueDate;

    /**
     * Every field must be present and not null.
     */
    public Reminder(ReminderDetails details, Priority priority, DueDate dueDate) {
        requireAllNonNull(details, priority, dueDate);
        this.details = new SimpleObjectProperty<>(details);
        this.priority = new SimpleObjectProperty<>(priority);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this(source.getDetails(), source.getPriority(), source.getDueDate());
    }

    public void setDetails(ReminderDetails details) {
        this.details.set(requireNonNull(details));
    }

    @Override
    public ObjectProperty<ReminderDetails> detailsProperty() {
        return details;
    }

    @Override
    public ReminderDetails getDetails() {
        return details.get();
    }


    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate.set(requireNonNull(dueDate));
    }

    @Override
    public ObjectProperty<DueDate> dueDateProperty() {
        return dueDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyReminder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(details, priority, dueDate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
