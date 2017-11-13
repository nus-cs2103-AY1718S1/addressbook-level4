package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//@@author deep4k
/**
 * Represents a Task in the addressbook
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private ObjectProperty<Header> header;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime lastUpdatedTime;

    // ================ Constructors ==============================

    /**
     * Constructor for a task without date/time
     */
    public Task(Header header) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = null;
        setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for  a task with only a deadline
     */
    public Task(Header header, Optional<LocalDateTime> deadline) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = deadline.orElse(null);
        this.setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for a task with a start time and end time
     */
    public Task(Header header, Optional<LocalDateTime> startDateTime,
                Optional<LocalDateTime> endDateTime) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = startDateTime.orElse(null);
        this.endDateTime = endDateTime.orElse(null);
        this.setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for a ReadOnlyTask copy
     */
    public Task(ReadOnlyTask source) {
        this(source.getHeader(), source.getStartDateTime(), source.getEndDateTime());
        if (source.isCompleted()) {
            this.setComplete();
        }
        this.setLastUpdatedTime(source.getLastUpdatedTime());
    }

    // ================ Getter methods ==============================

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Returns true if a task is not completed and has a start to end time which is not current but within
     * the threshold amount of days
     */
    @Override
    public boolean isUpcoming() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime thresholdTime = currentTime.plusDays(UPCOMING_DAYS_THRESHOLD);
        boolean isBeforeUpcomingDaysThreshold = getTaskTime().isBefore(thresholdTime);
        boolean isAfterCurrentTime = getTaskTime().isAfter(currentTime);

        return isBeforeUpcomingDaysThreshold && isAfterCurrentTime;

    }

    /**
     * Returns true if task is not complete and has a start to end time before current time
     */
    @Override
    public boolean isOverdue() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        boolean isBeforeCurrentTime = getTaskTime().isBefore(currentTime);

        return isBeforeCurrentTime;
    }

    /**
     * Returns true if a task has a start time or end time
     */
    @Override
    public boolean hasTime() {
        return getStartDateTime().isPresent() || getEndDateTime().isPresent();
    }

    /**
     * Returns true if the task has both start and end time
     */
    @Override
    public boolean isEvent() {
        return getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    @Override
    public boolean hasDeadline() {
        return !getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    /**
     * Returns the time when the task its most recent update (any change)
     */
    @Override
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Returns the start time if present, else returns end time
     */
    private LocalDateTime getTaskTime() {
        assert hasTime();
        return getStartDateTime().orElse(getEndDateTime().get());
    }

    // ================ Setter methods ==============================

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
        setLastUpdatedTimeToCurrent();
    }

    public void setComplete() {
        this.isCompleted = true;
        setLastUpdatedTimeToCurrent();
    }

    public void setIncomplete() {
        this.isCompleted = false;
        setLastUpdatedTimeToCurrent();
    }

    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime = startDateTime.orElse(null);
        setLastUpdatedTimeToCurrent();
    }

    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime = endDateTime.orElse(null);
        setLastUpdatedTimeToCurrent();
    }

    public void setLastUpdatedTime(LocalDateTime updatedTime) {
        this.lastUpdatedTime = updatedTime;
    }

    public void setLastUpdatedTimeToCurrent() {
        this.lastUpdatedTime = LocalDateTime.now().withNano(0);
    }

    // ================ Utility methods ==============================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    /**
     * Compares if this task is the same as task @code{other}
     * The task is different if
     * 1. Both tasks have different completion status
     * 2. Both tasks have different start and end dates
     * 3. Both tasks have different updated times
     * 4. One tasks has a different name than the other
     */
    @Override
    public int compareTo(Task other) {
        int comparedCompletionStatus = compareCompletionStatus(other);
        if (comparedCompletionStatus != 0) {
            return comparedCompletionStatus;
        }

        int comparedTaskTime = compareTaskTime(other);
        if (!isCompleted() && comparedTaskTime != 0) {
            return comparedTaskTime;
        }

        int comparedLastUpdatedTime = compareLastUpdatedTime(other);
        if (comparedLastUpdatedTime != 0) {
            return comparedLastUpdatedTime;
        }

        return compareHeader(other);
    }

    public int compareCompletionStatus(Task other) {
        return Boolean.compare(this.isCompleted(), other.isCompleted());
    }

    /**
     * Compares the start time of this task and task @code{other}
     * Both tasks are considered the same if both have no times or the start time is same
     */
    public int compareTaskTime(Task other) {
        if (this.hasTime() && other.hasTime()) {
            return this.getTaskTime().compareTo(other.getTaskTime());
        } else if (this.hasTime()) {
            return -1;
        } else if (other.hasTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareLastUpdatedTime(Task other) {
        return other.getLastUpdatedTime().compareTo(this.getLastUpdatedTime());
    }

    public int compareHeader(Task other) {
        return this.getHeader().toString().compareTo(other.getHeader().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, isCompleted, startDateTime, endDateTime);
    }

    public int syncCode() {
        return hashCode();
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
