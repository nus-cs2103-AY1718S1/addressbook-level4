//@@author duyson98

package seedu.address.model.reminder;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Reminder in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {

    ObjectProperty<Task> taskProperty();
    Task getTask();
    ObjectProperty<Priority> priorityProperty();
    Priority getPriority();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Message> messageProperty();
    Message getMessage();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTask().equals(this.getTask()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getDate().equals(this.getDate())
                && other.getMessage().equals(this.getMessage()));
    }

    /**
     * Formats the reminder as text, showing all reminder details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTask())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Date: ")
                .append(getDate())
                .append(" Message: ")
                .append(getMessage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
