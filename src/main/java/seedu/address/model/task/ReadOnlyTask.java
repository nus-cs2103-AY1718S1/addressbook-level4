package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

//@author chairz
/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<Priority> priorityProperty();
    Priority getPriority();
    ObjectProperty<DueDate> dueDateProperty();
    DueDate getDueDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription())
                && other.getPriority().equals(this.getPriority())
                && other.getDueDate().equals(this.getDueDate()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Priority: ")
                .append(getPriority())
                .append(" DueDate: ")
                .append(getDueDate());
        return builder.toString();
    }

}
