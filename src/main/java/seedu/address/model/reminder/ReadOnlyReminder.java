package seedu.address.model.reminder;
//@@author RonakLakhotia
import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Reminder.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {

    ObjectProperty<ReminderDetails> detailsProperty();
    ReminderDetails getDetails();
    ObjectProperty<Priority> priorityProperty();
    Priority getPriority();
    ObjectProperty<DueDate> dueDateProperty();
    DueDate getDueDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDetails().equals(this.getDetails()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getDueDate().equals(this.getDueDate()));
    }

    /**
     * Formats and returns the reminder as text, showing all reminder details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDetails())
                .append(" Details: ")
                .append(getPriority())
                .append(" Priority: ")
                .append(getDueDate())
                .append(" DueDate: ");
        return builder.toString();
    }

}
