//@@author srishag
package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Header> headerProperty();
    Header getHeader();
    ObjectProperty<Desc> descProperty();
    Desc getDesc();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader()) // state checks here onwards
                && other.getDesc().equals(this.getDesc())
                && other.getDeadline().equals(this.getDeadline()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getHeader())
                .append(" Desc: ")
                .append(getDesc())
                .append(" Deadline: ")
                .append(getDeadline());
        return builder.toString();
    }

}
