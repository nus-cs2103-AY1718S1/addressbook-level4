package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Task in the application.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<StartDate> startDateProperty();
    StartDate getStartDate();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();
    ObjectProperty<SingleEventDate> singleEventDateProperty();
    SingleEventDate getSingleEventDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getDeadline().equals(this.getDeadline())
                && other.getSingleEventDate().equals(this.getSingleEventDate()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription());
        if (!getStartDate().isEmpty()) {
            builder.append(" From: ").append(getStartDate());
        }
        if (!getDeadline().isEmpty()) {
            builder.append(" To: ").append(getDeadline());
        }
        if (!getSingleEventDate().isEmpty()) {
            builder.append(" On: ").append(getSingleEventDate());
        }
        return builder.toString();
    }
}
