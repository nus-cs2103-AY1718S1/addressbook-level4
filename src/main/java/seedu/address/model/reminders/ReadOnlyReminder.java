package seedu.address.model.reminders;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;

//@@author justinpoh
/**
 * A read-only immutable interface for a Reminder in iContacts.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {
    ObjectProperty<String> reminderProperty();
    String getReminder();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Time> timeProperty();
    Time getTime();
    ObjectProperty<Status> statusProperty();
    LocalDateTime getLocalDateTime();
    boolean isEventToday();
    boolean isEventWithinThreeDays();
    boolean hasEventPassed();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getReminder().equals(this.getReminder()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime()));
    }
}
