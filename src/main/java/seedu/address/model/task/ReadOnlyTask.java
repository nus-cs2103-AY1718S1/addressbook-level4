package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Header getHeader();

    boolean isCompleted();

    boolean isUpcoming();

    boolean isOverdue();

    boolean isEvent();

    boolean hasDeadline();

    boolean hasTime();

    Optional<LocalDateTime> getStartDateTime();

    Optional<LocalDateTime> getEndDateTime();

    LocalDateTime getLastUpdatedTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader()) // state checks here onwards
                && (other.isCompleted() == this.isCompleted())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime()));
    }

    /**
     * Formats the task as text, showing task header only.
     */
    default String getAsText() {
        return String.valueOf(getHeader()) + "\n";
    }

    /**
     * Formats the task as text, showing all task details
     */
    default String getDetailedText() {
        String completionState = (isCompleted()) ? "Completed" : "Incomplete";
        String startTime = (getStartDateTime().isPresent()) ? getStartDateTime().get().toString()
                : "None";
        String endTime = (getEndDateTime().isPresent()) ? getEndDateTime().get().toString()
                : "None";
        String lastUpdatedTime = getLastUpdatedTime().toString();

        final StringBuilder builder = new StringBuilder();
        builder.append("Task: ")
                .append(getHeader())
                .append(" Completion State: ")
                .append(completionState)
                .append(" Start Time: ")
                .append(startTime)
                .append(" End Time: ")
                .append(endTime)
                .append(" Last Updated Time: ")
                .append(lastUpdatedTime);
        return builder.toString();
    }
}
