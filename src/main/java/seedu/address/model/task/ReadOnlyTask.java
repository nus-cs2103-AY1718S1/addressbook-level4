package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

public interface ReadOnlyTask {

    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<StartDate> startDateProperty();
    StartDate getStartDate();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getDeadline().equals(this.getDeadline()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" From: ")
                .append(getStartDate())
                .append(" To: ")
                .append(getDeadline());
        return builder.toString();
    }
    
}
