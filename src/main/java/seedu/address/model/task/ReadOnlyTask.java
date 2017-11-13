package seedu.address.model.task;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author raisa2010
/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();
    ObjectProperty<EventTime> startTimeProperty();
    EventTime getStartTime();
    ObjectProperty<EventTime> endTimeProperty();
    EventTime getEndTime();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline()))
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime());
    }

    /**
     * Formats the task as text, showing all non-empty task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription());
        if (!getDeadline().isEmpty()) {
            builder.append(" Deadline: ").append(getDeadline());
        }
        if (getEndTime().isPresent() && !getStartTime().isPresent()) {
            builder.append(" At: ").append(getEndTime());
        }
        if (getStartTime().isPresent()) {
            builder.append(" At: ").append(getStartTime());
        }
        if (getEndTime().isPresent() && getStartTime().isPresent()) {
            builder.append(" - ").append(getEndTime());
        }
        if (!getTags().isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
